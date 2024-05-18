package com.books.entity;

import com.books.entity.enums.FileMediaType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoFile {
    @Id
    private Integer photoFileId;

    private String name;

    private String path;

    private String extensions;

    @Enumerated(EnumType.STRING)
    private FileMediaType mediaType;

    private Long size;

    @JsonBackReference
    @OneToMany(mappedBy = "photoFile", cascade = CascadeType.ALL)
    private List<Book> books;

    public static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }
}
