package com.MSGFoundation.service.impl;

import com.MSGFoundation.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;
    private final String bucketName = "msgf-documents";

    public String uploadFile(MultipartFile file, String filename) throws IOException {
        try {
            PutObjectRequest putObjectAclRequest = PutObjectRequest.builder()
                    .bucket("msgf-documents")
                    .key(filename)
                    .build();

            s3Client.putObject(putObjectAclRequest, RequestBody.fromBytes(file.getBytes()));
            return "File load successfully";
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public String downloadFile(String fileName) throws IOException {
        String localPath = "/Users/"+System.getProperty("user.name")+"/";
        if (!doesObjectExists(fileName)) {
            return "File has not exists";
        }
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        ResponseInputStream<GetObjectResponse> result = s3Client.getObject(request);

        try (FileOutputStream fos = new FileOutputStream(localPath+fileName)) {
            byte[] read_buf = new byte[1024];
            int read_len = 0;

            while((read_len = result.read(read_buf)) > 0){
                fos.write(read_buf,0,read_len);
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return "File has been downloaded successfully";
    }

    public boolean doesObjectExists(String objectKey) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            s3Client.headObject(headObjectRequest);
            return true;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return false;
            }
        }
        return true;
    }

    public String deleteFile(String filename) throws IOException {
        if(!doesObjectExists(filename)){
            System.out.println("File has not exists");
        }
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
            return "File has been deleted";
        } catch (S3Exception e){
            throw new IOException(e.getMessage());
        }
    }

    public String updateFile(MultipartFile file, String filename) throws IOException {
        if (!doesObjectExists(filename)) {
            return "File has not exists¿";
        }

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return "File has been updated successfully";
        } catch (IOException e) {
            throw new IOException("Error while updating: " + e.getMessage());
        }
    }

}
