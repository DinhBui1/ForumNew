package com.example.studentforum.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.cloud.vertexai.api.Part;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GenminiService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s";

    private final String API_KEY = "AIzaSyCWjSqvnAnOCCq6J3tdrAdXRYs0vZKWTjw";

    public String callApi(String prompt) {
        String text = "Kiểm tra xem đoạn văn bản này có chứa từ ngữ thô tục ở mức độ cao không. Nếu có thì trả về yes và chỉ ra từ vi phạm đó trong [ ]. Còn không thì trả về no" + prompt;
        String apiUrl = String.format(API_URL_TEMPLATE, API_KEY);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.put("text", text);
        contentNode.set("parts", partsNode);
        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.set("contents", contentNode);

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
        ObjectNode data;
        try {
            data = objectMapper.readValue(response.getBody(), ObjectNode.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }

        return data.get("candidates").get(0).get("content").get("parts").get(0).get("text").toString();
    }
}
