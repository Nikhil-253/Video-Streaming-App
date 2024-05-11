package com.example.iitdvideohub.controller;

import com.example.iitdvideohub.model.BlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class VideoStreamController {
    @Autowired
    private BlobStorageService blobStorageService;


    public static final Logger log = Logger.getLogger(VideoStreamController.class.getName());

/*    @GetMapping("/stream/{videoPath}")
    public ResponseEntity<InputStreamResource> streamVideo(@PathVariable String videoPath) throws FileNotFoundException {
        File file = new File(VIDEO_DIRECTORY + videoPath);
        if (file.exists()) {
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4"))
                    .body(inputStreamResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

//    @Autowired
//    private VideoCatalogService videoCatalogService;

    @Autowired
    private VideoInfoController videoInfoController;

    @GetMapping("/stream/with-id/{videoInfoId}")
    public ResponseEntity<InputStreamResource> streamVideoById(@PathVariable Long videoInfoId) throws FileNotFoundException {
        String videoPath = videoInfoController.findPathById(videoInfoId);
//        String url = "http://localhost:9093/video-info/find-path-by-id/3";
//        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).build();
        log.log(Level.INFO, "Resolved movie path = ", videoPath);
        return streamVideoFromBlob("videos",videoPath);
    }

    @GetMapping("/stream/{containerName}/{blobName}")
    public ResponseEntity<InputStreamResource> streamVideoFromBlob(@PathVariable String containerName, @PathVariable String blobName) {
        try {
            InputStream videoStream = blobStorageService.getVideoStream(containerName, blobName);
            if (videoStream != null) {
                InputStreamResource inputStreamResource = new InputStreamResource(videoStream);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("video/mp4"))
                        .body(inputStreamResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
