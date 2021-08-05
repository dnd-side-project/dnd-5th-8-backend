package com.dnd.eight.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    //1. MultipartFile -> File로 변환
    public String upload(File file, String dir) throws IOException {
        //S3에 저장할 file name 생성
        String origName = file.getName();
        String ext = origName.substring(origName.lastIndexOf('.'));
        String uploadFileName = getUuid() + ext;

        //File S3 업로드
        String s3FileName = dir + "/" + uploadFileName;
        String profileUrl = uploadS3(file, s3FileName);

        return profileUrl;
    }
    public String upload(MultipartFile multipartFile, String dir) throws IOException {
        //S3에 저장할 file name 생성
        String origName = multipartFile.getOriginalFilename();
        String ext = origName.substring(origName.lastIndexOf('.'));
        String uploadFileName = getUuid() + ext;

        //MultipartFile -> File 변환
        File uploadFile = new File(System.getProperty("user.dir") + uploadFileName); // ** 원래코드 : new File(uploadFileName)
//        System.out.println("uploadFile.getName() = " + uploadFile.getName()); //**
        multipartFile.transferTo(uploadFile);

        //File S3 업로드
        String s3FileName = dir + "/" + uploadFileName;
        String profileUrl = uploadS3(uploadFile, s3FileName);

        uploadFile.delete();
        return profileUrl;
    }
    //1.1 파일 이름 unique하게 변경 UUID
    private static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    //2. S3에 업로드 (put)
    private String uploadS3(File uploadFile, String s3FileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, s3FileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, s3FileName).toString();
    }

    public void delete(String profileUrl){
        String deleteFileName = "profile" + profileUrl.substring(profileUrl.lastIndexOf("/"));
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, deleteFileName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    public ResponseEntity<byte[]> download(String key) throws IOException {
        String ext = key.substring(key.lastIndexOf(".")+1);
        S3Object object = amazonS3Client.getObject(bucket, key);
        S3ObjectInputStream objectInputStream = object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        if(ext.equals("png"))
            httpHeaders.setContentType(MediaType.IMAGE_PNG);
        else
            httpHeaders.setContentType(MediaType.IMAGE_JPEG);

        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", key);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public File imgUrlToFile(String imgUrl) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String ext = imgUrl.substring(imgUrl.lastIndexOf("."));

        byte[] profileByte = restTemplate.getForObject(imgUrl, byte[].class);
        File outputFile = new File("profile" + ext);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(profileByte);

        return outputFile;
    }
}
