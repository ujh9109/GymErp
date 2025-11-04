package com.example.gymerp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/public")
public class PublicHolidayController {

    @GetMapping("/holidays")
    public ResponseEntity<String> getHolidays(@RequestParam int year) {
        String serviceKey = "22a7450681f54029593c12edd88ecfe7e3a91e6338559ca77a3398d25ec6c9b6"; // 🔹 실제 키로 바꿔야 함
        String url = String.format(
            "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?solYear=%d&numOfRows=100&_type=json&ServiceKey=%s",
            year, serviceKey
        );

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok(response);
    }
}
