package com.books.mapper;

import com.books.dto.book.CreateBookDto;
import com.books.entity.Book;
import com.books.entity.Category;
import com.books.entity.PhotoFile;
import com.books.exception.ResourceNotFoundException;
import com.books.repository.category.CategoryRepository;
import com.books.repository.storage.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final FileRepository fileRepository;
    private final CategoryRepository categoryRepository;

    public Book dtoToBook(CreateBookDto dto) {
        Book book = new Book();
        book.setBookName(dto.getBookName());
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());
        book.setTitle(dto.getTitle());
        book.setLanguage(dto.getLanguage());
        book.setPublishedYear(dto.getPublishedYear());
        book.setQuantity(dto.getQuantity());
        book.setCover(dto.getCover());
        book.setPrice(dto.getPrice());

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow
                (() -> new ResourceNotFoundException("Category", "Category ID", dto.getCategoryId()));

        book.setCategory(category);

        PhotoFile photoFile = fileRepository.findById(dto.getPhotoFileId()).orElseThrow
                (() -> new ResourceNotFoundException("Photo File", "Photo File ID", dto.getPhotoFileId()));

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
        dto.setPrice(book.getPrice());

        if (book.getCategory() != null) {
            dto.setCategoryId(book.getCategory().getCategoryId());
        }

        if (book.getPhotoFile() != null) {
            dto.setPhotoFileId(book.getPhotoFile().getPhotoFileId());
        }

        return dto;
    }
}
