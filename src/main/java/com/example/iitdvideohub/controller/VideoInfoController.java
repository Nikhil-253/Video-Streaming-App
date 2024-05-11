package com.example.iitdvideohub.controller;

import com.example.iitdvideohub.model.VideoInfo;
import com.example.iitdvideohub.model.VideoInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VideoInfoController {

    @Autowired
    private VideoInfoRepository repository;

    @PostMapping("/video-info/save")
    public List<VideoInfo> saveAll(@RequestBody List<VideoInfo> videoInfoList) {
        return repository.saveAll(videoInfoList);
    }

    @GetMapping("/video-info/list")
    public List<VideoInfo> getAll() {
        return repository.findAll();
    }

    @GetMapping("/video-info/find-path-by-id/{videoInfoId}")
    public String findPathById(@PathVariable Long videoInfoId) {
        var videoInfoOptional = repository.findById(videoInfoId);
        return videoInfoOptional.map(VideoInfo::getPath).orElse(null);
    }
}
