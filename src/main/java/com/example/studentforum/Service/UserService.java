package com.example.studentforum.Service;

import com.example.studentforum.Config.RedisManager;
import com.example.studentforum.DTO.UserDTO;
import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.IsBanRepository;
import com.example.studentforum.Repository.RefreshTokenRepository;
import com.example.studentforum.Repository.RoleRepository;
import com.example.studentforum.Repository.UserRepository;
import com.example.studentforum.exception.ErrorResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.modelmapper.ModelMapper;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private IsBanRepository isBanRepository;
    private ConcurrentHashMap<String, String> confirmationCodeMap = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(16);
    private ScheduledExecutorService banService = Executors.newScheduledThreadPool(16);

    public List<User> getallUser(int limit, int pacing) {

        int offset = (pacing - 1);
        Pageable pageable = PageRequest.of(offset, limit);
        return userRepository.getUser(pageable);
    }

    public User account_update(User user) {
        User u = userRepository.getUserById(user.getUserid());
        if (u == null) {
            return null;
        }
        u.setFullname(user.getFullname());
        u.setBirthday(user.getBirthday());
        u.setGender(user.getGender());
        u.setPhone(user.getPhone());
        u.setBio(user.getBio());
        u.setAddress(user.getAddress());
        u.setImage(user.getImage());
        u.setColor(user.getColor());
        userRepository.updateaccount(u);
        return u;
    }

    public User getUserByid(String id) {
        return userRepository.getUserById(id);
    }

    public ResponseEntity<String> registerUser(UserDTO userDTO) {
        try {
            User u = mapper.map(userDTO, User.class);
            if (userRepository.getUserByUsername(u.getUsername()) != null && userRepository.getUserByEmail(u.getEmail()) != null)
                return ResponseEntity.status(400).body("User or email exit");
//            String[] parts = u.getEmail().split("@");
//            String MSSV = parts[0];
//            u.setMssv(MSSV);
            Role r = new Role(2);
            UUID randomUUID = UUID.randomUUID();
            String randomID = randomUUID.toString();
            u.setUserid(randomID);
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setImage(null);
            u.setType("UP");
            u.setCreateday(LocalDateTime.now());
            IsBan ib = isBanRepository.getIsBanByIsbanid(0);
            u.setIsban(ib);
            u.setStatus(0);
            u.setRole(r);
            u.setReputation(0);
            RefreshToken rf = new RefreshToken();
            rf.setUser_refresh(u);

            userRepository.save(u);
            refreshTokenRepository.save(rf);
            return ResponseEntity.ok("Register Success");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("Failure");
        }
    }

    public ResponseEntity<Object> login(UserDTO userDTO) {
        try {
            User user = mapper.map(userDTO, User.class);
            User u = userRepository.getUserByUsername(user.getUsername());
            if (u == null) {
                return ResponseEntity.status(404).body("Username not exit");
            } else {
                if (passwordEncoder.matches(userDTO.getPassword(), u.getPassword()) == false) {
                    return ResponseEntity.status(403).body("Password wrong");
                }
            }
            if (u.getIsban().getIsbanid() == 3 || u.getIsban().getIsbanid() == 4 || u.getIsban().getIsbanid() == 5 || u.getIsban().getIsbanid() == 6) {
                return ResponseEntity.status(403).body("User Has Been Exit Ban List");
            }
            String access = tokenService.generateAccessToken(u.getUserid(), u.getRole().getRoleid());
            String refresh = tokenService.generateRefreshToken(u.getUserid(), u.getRole().getRoleid());
            ObjectMapper objectMapper1 = new ObjectMapper();
            ObjectNode jsonResponse = objectMapper1.createObjectNode();
            jsonResponse.put("id", u.getUserid());
            jsonResponse.put("name", u.getFullname());
            jsonResponse.put("email", u.getEmail());
            jsonResponse.put("accesstoken", access);
            jsonResponse.put("refreshtoken", refresh);
            jsonResponse.put("roleid", u.getRole().getRoleid());

            userRepository.setStatusUsser(u.getUserid(), 1);
            Date currentDate = new Date();
            refreshTokenRepository.updateRefreshToken(refresh, currentDate, u.getUserid());
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(403).body("Login Failure");
        }
    }

    public ResponseEntity<Object> logingoogle(String accesstoken) {
        try {
            String accessToken = accesstoken;
            String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Object.class);
            Object responseBody = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.valueToTree(responseBody);

            String sub = jsonNode.get("sub").asText();
            String name = jsonNode.get("name").asText();
            String email = jsonNode.get("email").asText();
            String picture = jsonNode.get("picture").asText();


            User u = userRepository.getUserById(sub);

            Role role = new Role(2);

            String access = tokenService.generateAccessToken(sub, role.getRoleid());
            String refresh = tokenService.generateRefreshToken(sub, role.getRoleid());

            ObjectMapper objectMapper1 = new ObjectMapper();
            ObjectNode jsonResponse = objectMapper1.createObjectNode();
            jsonResponse.put("id", sub);
            jsonResponse.put("name", name);
            jsonResponse.put("email", email);
            jsonResponse.put("accesstoken", access);
            jsonResponse.put("refreshtoken", refresh);
            jsonResponse.put("roleid", role.getRoleid());
            jsonResponse.put("path", role.getPath());

            Date currentDate = new Date();
            if (u == null) {
                User user = new User();

                user.setUserid(sub);
                user.setFullname(name);
                user.setUsername(email);
                user.setEmail(email);
                user.setImage(picture);
                user.setCreateday(LocalDateTime.now());
                IsBan ib = isBanRepository.getIsBanByIsbanid(0);
                user.setIsban(ib);
                user.setStatus(0);
                user.setRole(role);
                user.setType("GG");
                user.setReputation(0);
                userRepository.save(user);

                RefreshToken refreshToken = new RefreshToken();
                refreshToken.setUser_refresh(user);
                refreshToken.setToken(refresh);
                refreshToken.setCreated(currentDate);
                refreshTokenRepository.save(refreshToken);
            } else {
                if (u.getIsban().getIsbanid() == 3 || u.getIsban().getIsbanid() == 4 || u.getIsban().getIsbanid() == 5 || u.getIsban().getIsbanid() == 6) {
                    return ResponseEntity.status(403).body("User Has Been Exit Ban List");
                }
                userRepository.setStatusUsser(u.getUserid(), 1);
                refreshTokenRepository.updateRefreshToken(refresh, currentDate, u.getUserid());
            }
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("Login Failure");
        }
    }

    public ResponseEntity<Object> refreshToken(String refreshtoken) {
        if (tokenService.isTokenExpired(refreshtoken) == false) {
            RefreshToken rt = refreshTokenRepository.findRefreshToken(refreshtoken);
            if (rt != null) {
                User u = userRepository.getUserById(rt.getUser_refresh().getUserid());
                String access = tokenService.generateAccessToken(u.getUserid(), u.getRole().getRoleid());
                ObjectMapper objectMapper1 = new ObjectMapper();
                ObjectNode jsonResponse = objectMapper1.createObjectNode();
                jsonResponse.put("accesstoken", access);
                return ResponseEntity.ok(jsonResponse);
            } else {
                return ResponseEntity.status(401).body("Invalid refresh token");
            }
        } else {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

    }

    public ResponseEntity<String> forgotPassword(String email) {
        SimpleMailMessage message = null;
        try {

            String code = generateRandomString();
            message = new SimpleMailMessage();
            message.setFrom("20110633@student.hcmute.edu.vn");
            message.setTo(email);
            message.setSubject("Verification Code");
            message.setText("Your password reset code is: " + code + "\nMã xác thực sẽ hết hạn sau 10 phút");
            mailSender.send(message);
            Boolean mailKey = confirmationCodeMap.containsKey(email);
            if (mailKey == true) {
                confirmationCodeMap.remove(email);
                confirmationCodeMap.put(email, code);
            } else {
                confirmationCodeMap.put(email, code);
            }
            executorService.schedule(() -> confirmationCodeMap.remove(email), 10, TimeUnit.MINUTES);
            return ResponseEntity.ok("Code have been given");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error");
        }
    }


    public ResponseEntity<String> verifyCode(String email, String code) {
        String storedCode = confirmationCodeMap.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            confirmationCodeMap.remove(email);
            return ResponseEntity.ok("Verify Success");
        }
        return ResponseEntity.ok("Code is wrong");
    }

    public static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public ResponseEntity<String> resetPassword(UserDTO userDTO) {
        try {
            User user = mapper.map(userDTO, User.class);
            User u = userRepository.getUserByEmail(user.getEmail());
            u.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.changePassword(u.getEmail(), u.getPassword());
            return ResponseEntity.ok("Change password success");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Change password failure");
        }
    }

    public User findUserById(String userid) {
        return userRepository.getUserById(userid);
    }

    public void updateReputation(String userid, int reputation) {
        User u = userRepository.getUserById(userid);
        u.setReputation(u.getReputation() + reputation);
        userRepository.updateReputation(u);
    }

    public List<User> getTopReputationUser() {
        return userRepository.getTopReputation();
    }

    public User checkBanUser(String userid, int isbanid) {
        User u = userRepository.getUserById(userid);
        IsBan ib = isBanRepository.getIsBanByIsbanid(isbanid);
        u.setIsban(ib);

        if (isbanid == 0) {
            u.setReputation(-50);
            String content = "Hệ thống: Tài khoản của bạn đã hết lệnh cấm";
            Notice n = noticeService.createNotice(userid, content, 0, 0);
        } else {
            String content = "Hệ thống: Tài khoản của bạn đã bị: " + u.getIsban().getDescription() + "\n Lệnh có hiệu lực từ thời điểm này";
            Notice n = noticeService.createNotice(userid, content, 0, 0);
        }
        userRepository.banUser(u);
        if (isbanid == 1 || isbanid == 3) {
            scheduleUnBan(userid, 3);
        }
        if (isbanid == 2 || isbanid == 4) {
            scheduleUnBan(userid, 5);
        }
        if (isbanid == 5) {
            scheduleUnBan(userid, 10);
        }
        return u;
    }

    public void scheduleUnBan(String userid, int dayban) {
        banService.schedule(() -> checkBanUser(userid, 0), dayban, TimeUnit.DAYS);
    }

    public List<User> getListLowReputationUser() {
        return userRepository.getLowReputation();
    }

    public List<User> getListBanUser() {
        return userRepository.getListBanUser();
    }

    public String logout(String userid) {
        userRepository.setStatusUsser(userid, 0);
        return "Logout Success";
    }

    public Publisher<User> subStatusUserByUserid(String userid) {
        return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            User u = userRepository.getUserById(userid);
            subscriber.onNext(u);

        }, 0, 1, TimeUnit.SECONDS);
    }

    public int[] statisticUser(int year) {
        List<User> users = userRepository.statisticUser(year);
        int[] statistic = new int[12];
        for (User u : users) {
            int month = u.getCreateday().getMonthValue();
            statistic[month - 1]++;
        }

        return statistic;
    }

    public List<User> getUserbayKeyword(String keyword, String userid) {
        Jedis jedis = RedisManager.getConnection();
        jedis.lpush(userid, keyword);
        return userRepository.getUserByKeyword(keyword);
    }
}
