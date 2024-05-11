package com.example.iitdvideohub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Component
public class VideoCatalogService {

    public static final String baseUrl = "http://localhost:9092";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getVideoPath")
    public String getVideoPath(Long videoInfoId) {
        var response = restTemplate.getForObject(baseUrl +"/video-info/find-path-by-id/{videoInfoId}", String.class, videoInfoId);
        return response;
    }
}
