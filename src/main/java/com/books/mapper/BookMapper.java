package com.books.mapper;

import com.books.dto.book.CreateBookDto;
import com.books.dto.book.GetBookDto;
import com.books.dto.book.UpdateBookDto;
import com.books.dto.category.UpdateCategoryDto;
import com.books.entity.Book;
import com.books.entity.Category;

public class BookMapper {

    //TODO Create Book Dto
    public static Book mapCreateBookDtoToBook(CreateBookDto createBookDto) {
        Book book = new Book();
        book.setBookName(createBookDto.getBookName());
        book.setAuthor(createBookDto.getAuthor());
        book.setTitle(createBookDto.getTitle());
        book.setPublisher(createBookDto.getPublisher());
        book.setLanguage(createBookDto.getLanguage());
        book.setCover(createBookDto.getCover());
        book.setImage(createBookDto.getImage());
        book.setPublishedYear(createBookDto.getPublishedYear());
        book.setQuantity(createBookDto.getQuantity());

        Category category = new Category();
        category.setCategoryId(createBookDto.getCategoryId());
        book.setCategory(category);
        return book;
    }

    //TODO Get Category Dto
    public static GetBookDto mapBookToGetBookDto(Book book) {
        GetBookDto dto = new GetBookDto();
        dto.setBookId(book.getBookId());
        dto.setBookName(book.getBookName());
        dto.setAuthor(book.getAuthor());
        dto.setTitle(book.getTitle());
        dto.setPublisher(book.getPublisher());
        dto.setLanguage(book.getLanguage());
        dto.setCover(book.getCover());
        dto.setImage(book.getImage());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setQuantity(book.getQuantity());
        dto.setIsbn(book.getIsbn());
        dto.setCategoryId(book.getCategory().getCategoryId());
        return dto;
    }

    //TODO Update Category Dto
    public static UpdateBookDto mapBookToUpdateBookDto(Book book) {
        UpdateBookDto dto = new UpdateBookDto();
        dto.setBookId(book.getBookId());
        dto.setBookName(book.getBookName());
        dto.setAuthor(book.getAuthor());
        dto.setTitle(book.getTitle());
        dto.setPublisher(book.getPublisher());
        dto.setLanguage(book.getLanguage());
        dto.setCover(book.getCover());
        dto.setImage(book.getImage());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setQuantity(book.getQuantity());
        dto.setCategoryId(book.getCategory().getCategoryId());
        return dto;
    }

    public static Book mapUpdateBookDtoToBook(UpdateBookDto updateBookDto){
        Book book = new Book();
        book.setBookId(updateBookDto.getBookId());
        book.setBookName(updateBookDto.getBookName());
        book.setAuthor(updateBookDto.getAuthor());
        book.setTitle(updateBookDto.getTitle());
        book.setPublisher(updateBookDto.getPublisher());
        book.setLanguage(updateBookDto.getLanguage());
        book.setCover(updateBookDto.getCover());
        book.setImage(updateBookDto.getImage());
        book.setPublishedYear(updateBookDto.getPublishedYear());
        book.setQuantity(updateBookDto.getQuantity());
        book.getCategory().setCategoryId(updateBookDto.getCategoryId());
        return book;
    }


}
