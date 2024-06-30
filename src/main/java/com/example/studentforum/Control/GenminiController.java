package com.example.studentforum.Control;

import com.example.studentforum.Service.GenminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Response;

@RestController
@RequestMapping("/ai")
public class GenminiController {

    @Autowired
    private GenminiService genminiService;

    @GetMapping("/summary")
    public ResponseEntity<Object> summaryText(@RequestBody String prompt) {
        try {
            String response = genminiService.callApi(prompt);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
