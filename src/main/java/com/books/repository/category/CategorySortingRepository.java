package com.books.repository.category;

import com.books.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySortingRepository extends PagingAndSortingRepository<Category, Integer> {
}
