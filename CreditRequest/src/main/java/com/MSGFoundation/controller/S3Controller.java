package com.MSGFoundation.controller;

import com.MSGFoundation.service.impl.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class S3Controller {
    private final S3ServiceImpl s3Service;

    @PostMapping("/upload")
    private String uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        return s3Service.uploadFile(file,"nombrearchivo");
    }

    @GetMapping("/download/{filename}")
    public String downloadFile(@PathVariable("filename") String filename) throws IOException {
        return s3Service.downloadFile(filename);
    }
}
