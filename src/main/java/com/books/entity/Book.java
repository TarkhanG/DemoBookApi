package com.books.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @NotBlank(message = "Book name cannot be blank")
    private String bookName;

    @NotBlank(message = "Author name cannot be blank")
    private String author;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Column(unique = true)
    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;

    @NotBlank(message = "Publisher cannot be blank")
    private String publisher;

    @NotBlank(message = "Language cannot be blank")
    private String language;

    @NotBlank(message = "Cover description cannot be blank")
    private String cover;

    @NotBlank(message = "Image URL cannot be blank")
    private String image;

    @NotNull(message = "Published year must be provided")
    private int publishedYear;

    @NotNull(message = "Quantity must be provided")
    private int quantity;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
