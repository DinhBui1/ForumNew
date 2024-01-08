package com.example.studentforum.Control;

import com.example.studentforum.DTO.UserDTO;
import com.example.studentforum.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class TokenController {
    @Autowired
    private UserService userService;

    @PostMapping("/logingoogle")
    public ResponseEntity<Object> getUserInfoGoogle(@RequestParam("accesstoken") String accesstoken) {
        return userService.logingoogle(accesstoken);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> getUserInfo(@ModelAttribute UserDTO userDTO) {
        return userService.login(userDTO);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<Object> refreshToken(@RequestParam("refreshtoken") String refreshtoken) {
        return userService.refreshToken(refreshtoken);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@ModelAttribute UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @GetMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
        return userService.forgotPassword(email);
    }


    @GetMapping("/verification-code")
    public ResponseEntity<String> verifyCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        return userService.verifyCode(email, code);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@ModelAttribute UserDTO userDTO) {
        return userService.resetPassword(userDTO);
    }
}
