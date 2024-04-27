package com.example.studentforum;

import com.example.studentforum.Config.RedisManager;
import com.opencsv.CSVWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class StudentForumApplication {

//    private static String jdbcURL = "jdbc:mysql://localhost:3306/forumnew";
//
//    private static String username = "root";
//
//    private static String password = "dinh123";

    public static void main(String[] args) {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                createListSuggest();
//            }
//        }, 0, 30 * 1000);
        RedisManager.openConnection();
        SpringApplication.run(StudentForumApplication.class, args);
        RedisManager.closeConnection();
    }

//    public static void createListSuggest() {
//        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password)) {
//            String sql = "SELECT v.userid, p.title, p.content, t.topicname\n" +
//                    "FROM viewpost v  \n" +
//                    "INNER JOIN post p ON v.postid = p.postid  \n" +
//                    "INNER JOIN post_topic pt ON pt.postid = p.postid\n" +
//                    "INNER JOIN topic t ON t.topicid = pt.topicid";
//            Statement statement = conn.createStatement();
//            ResultSet result = statement.executeQuery(sql);
//            String csvFilePath = "/C:/Users/ACER/Documents/GitHub/ForumNew/src/main/resources/data/output.csv";
//
//            File file = new File(csvFilePath);
//            if (file.exists()) {
//                file.delete();
//            }
//            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, StandardCharsets.UTF_8))) {
//                writer.writeAll(result, true);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


}
