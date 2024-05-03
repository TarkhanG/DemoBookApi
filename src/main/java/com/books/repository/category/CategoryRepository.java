package com.books.repository.category;

import com.books.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryByCategoryName(String name);
    List<Category> findByCategoryNameStartingWithIgnoreCase(String name);
}
