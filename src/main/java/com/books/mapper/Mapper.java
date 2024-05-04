package com.books.mapper;

import com.books.dto.book.CreateBookDto;
import com.books.entity.Book;
import com.books.entity.Category;
import com.books.entity.PhotoFile;

public class Mapper {

    public static Book dtoToBook(CreateBookDto dto) {
        Book book = new Book();
        book.setBookName(dto.getBookName());
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());
        book.setTitle(dto.getTitle());
        book.setLanguage(dto.getLanguage());
        book.setPublishedYear(dto.getPublishedYear());
        book.setQuantity(dto.getQuantity());
        book.setCover(dto.getCover());

        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        book.setCategory(category);

        PhotoFile photoFile = new PhotoFile();
        photoFile.setPhotoFileId(dto.getPhotoFileId());
        book.setPhotoFile(photoFile);

        return book;
    }

    public static CreateBookDto toCreateBookDto(Book book) {
        CreateBookDto dto = new CreateBookDto();
        dto.setBookName(book.getBookName());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setTitle(book.getTitle());
        dto.setLanguage(book.getLanguage());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setQuantity(book.getQuantity());
        dto.setCover(book.getCover());

        if (book.getCategory() != null) {
            dto.setCategoryId(book.getCategory().getCategoryId());
        }

        if (book.getPhotoFile() != null) {
            dto.setPhotoFileId(book.getPhotoFile().getPhotoFileId());
        }

        return dto;
    }
}
