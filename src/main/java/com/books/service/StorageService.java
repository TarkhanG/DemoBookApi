package com.books.service;

import com.books.entity.PhotoFile;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    Integer uploadFile(MultipartFile file);
    byte[] downloadFile(String fileName);
    String deleteFile(String fileName);
    Page<PhotoFile> getAllPhotos(int size, int pageSize);
}
