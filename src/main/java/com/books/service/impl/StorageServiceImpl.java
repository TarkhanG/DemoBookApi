package com.books.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.books.entity.PhotoFile;
import com.books.entity.enums.FileMediaType;
import com.books.exception.FileUploadException;
import com.books.repository.storage.FileRepository;
import com.books.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);
    @Autowired
    private AmazonS3 s3Client;

    private final FileRepository fileRepository;

    @Value("${application.bucket.name}")
    private String bucketName;

    public StorageServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Override
    public String uploadFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            throw new FileUploadException("Only JPG and PNG files can be uploaded.");
        }
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();

        // Dosyanın s3'de saklandığı bilgileri alarak File entity'sini oluştur
        PhotoFile newFile = PhotoFile.builder()
                .name(file.getOriginalFilename())
                .path(fileName)
                .extensions(PhotoFile.getFileExtension(fileName))
                .mediaType(FileMediaType.IMAGE)
                .size(file.getSize())
                .build();

        PhotoFile savedFile = fileRepository.save(newFile);
        return "File uploaded : " + fileName;
    }

    @Override
    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
           byte[] content = IOUtils.toByteArray(inputStream);
           return content;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        fileRepository.deleteByPath(fileName);
        return "File deleted : " + fileName;
    }

    @Override
    public Page<PhotoFile> getAllPhotos(int size, int pageSize) {
        Pageable pageable = PageRequest.of(size, pageSize);
        Page<PhotoFile> photoFiles = fileRepository.findAll(pageable);
        return photoFiles;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

}
