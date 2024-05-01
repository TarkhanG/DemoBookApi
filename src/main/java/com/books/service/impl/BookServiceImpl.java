package com.books.service.impl;

import com.books.dto.book.CreateBookDto;
import com.books.dto.book.GetBookDto;
import com.books.dto.book.UpdateBookDto;
import com.books.entity.Book;
import com.books.exception.ResourceNotFoundException;
import com.books.mapper.BookMapper;
import com.books.repository.book.BookRepository;
import com.books.repository.book.BookSortingRepository;
import com.books.service.BookService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private BookSortingRepository bookSortingRepository;
    private ModelMapper modelMapper;

    @Override
    public Optional<GetBookDto> getBookById(Integer id) {
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, GetBookDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", String.valueOf(id)));
    }

    @Override
    public UpdateBookDto updateBook(UpdateBookDto dto) {
        Optional<Book> book = bookRepository.findById(dto.getBookId());
        if (book.isPresent()) {
            Book result = BookMapper.mapUpdateBookDtoToBook(dto);
            bookRepository.save(result);
            return BookMapper.mapBookToUpdateBookDto(result);
        }
        throw new ResourceNotFoundException("Category", "ID", String.valueOf(dto.getCategoryId()));
    }

    @Override
    public Book deleteBook(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
        }
        throw new ResourceNotFoundException("Category", "ID", String.valueOf(id));
    }


    @Override
    public Book createBook(CreateBookDto dto) {
        Book result = BookMapper.mapCreateBookDtoToBook(dto);
        result.setIsbn(generateISBN());
        return bookRepository.save(result);
    }

    //TODO ISBN Generator
    private String generateISBN() {
        String isbn13Prefix = "978"; // ISBN-13'un başlangıç kodu
        String isbnBody = RandomStringUtils.randomNumeric(9); // 9 xanali random reqemler
        String isbn12 = isbn13Prefix + isbnBody;
        // ISBN-13 ucun son xana hesaplanır
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += (i % 2 == 0) ? Character.getNumericValue(isbn12.charAt(i)) : Character.getNumericValue(isbn12.charAt(i)) * 3;
        }
        int remainder = sum % 10;
        int checkDigit = (10 - remainder) % 10;
        return isbn12 + checkDigit; // ISBN-13 numarası
    }


    @Override
    public List<Book> getAllBooks(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Book> categoryPage = bookSortingRepository.findAll(pageable);
        return categoryPage.toList();
    }
}
