package com.example.gymerp.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/v1/ai")
public class AiController {

    private final RestTemplate restTemplate = new RestTemplate();

    // AI 예측 요청
    @PostMapping("/predict/members")
    public Map<String, Object> predictMembers(@RequestBody Map<String, Object> requestData) {
        String pythonUrl = "http://localhost:5000/predict/members";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(pythonUrl, requestData, Map.class);
            return response.getBody();
        } catch (Exception e) {
            return Map.of("error", "Python 서버 연결 실패: " + e.getMessage());
        }
    }
}
