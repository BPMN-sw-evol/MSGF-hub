package com.MSGFoundation.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    String uploadFile(MultipartFile file, String filename) throws IOException;
    String downloadFile(String fileName) throws IOException;
    boolean doesObjectExists(String objectKey) throws IOException;
    String deleteFile(String filename) throws IOException;
    String updateFile(MultipartFile file, String filename) throws IOException;
}
