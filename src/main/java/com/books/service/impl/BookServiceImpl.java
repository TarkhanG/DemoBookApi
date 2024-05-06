package com.books.service.impl;

import com.books.dto.book.CreateBookDto;
import com.books.dto.book.GetBookByCategory;
import com.books.dto.book.GetBookDto;
import com.books.dto.book.UpdateBookDto;
import com.books.entity.Book;
import com.books.entity.Category;
import com.books.entity.PhotoFile;
import com.books.exception.FileUploadException;
import com.books.exception.GlobalExceptionHandler;
import com.books.exception.ResourceNotFoundException;
import com.books.mapper.Mapper;
import com.books.repository.book.BookRepository;
import com.books.service.BookService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final Mapper mapper;

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
        return isbn12 + checkDigit; // ISBN-13
    }

    @Override
    public CreateBookDto addBook(CreateBookDto createBookDto) {
            Book book = mapper.dtoToBook(createBookDto);
            book.setIsbn(generateISBN());
            Book addedBook = bookRepository.save(book);
            return Mapper.toCreateBookDto(addedBook);
    }

    @Override
    public Page<GetBookDto> getAllBooks(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Book> books = bookRepository.findAll(pageable);
        Page<GetBookDto> result = books.map(book -> modelMapper.map(book, GetBookDto.class));
        return result;
    }

    @Override
    public UpdateBookDto updateBook(UpdateBookDto updateBookDto, Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book","Book id ", id));

        book.setBookName(updateBookDto.getBookName());
        book.setAuthor(updateBookDto.getAuthor());
        book.setTitle(updateBookDto.getTitle());
        book.setPublisher(updateBookDto.getPublisher());
        book.setLanguage(updateBookDto.getLanguage());
        book.setCover(updateBookDto.getCover());
        book.setPublishedYear(updateBookDto.getPublishedYear());
        book.setQuantity(updateBookDto.getQuantity());

        Category category = new Category();
        category.setCategoryId(updateBookDto.getCategoryId());
        book.setCategory(category);

        PhotoFile photoFile = new PhotoFile();
        photoFile.setPhotoFileId(updateBookDto.getPhotoFileId());
        book.setPhotoFile(photoFile);

        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, UpdateBookDto.class);
    }


    @Override
    public void deleteBook(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "Book id", id));
        bookRepository.delete(book);
    }

    @Override
    public GetBookDto getBook(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "Book id ", id));
        return modelMapper.map(book, GetBookDto.class);
    }

    @Override
    public GetBookDto findBookByBookName(String bookName) {
           Book book = bookRepository.findBookByBookName(bookName);
           if (book == null) {
               throw new ResourceNotFoundException("Book", "Book name ", bookName);
           }
           GetBookDto result = modelMapper.map(book, GetBookDto.class);
           return result;
    }

    @Override
    public GetBookByCategory getBookByCategory(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "Book id ", id));

        GetBookByCategory result = modelMapper.map(book, GetBookByCategory.class);
        result.setCategoryName(book.getCategory().getCategoryName());
        result.setDescription(book.getCategory().getDescription());

        return result;
    }

}
