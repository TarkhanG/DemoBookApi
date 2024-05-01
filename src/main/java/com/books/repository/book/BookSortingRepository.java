package com.books.repository.book;

import com.books.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSortingRepository extends PagingAndSortingRepository<Book, Integer> {
}
