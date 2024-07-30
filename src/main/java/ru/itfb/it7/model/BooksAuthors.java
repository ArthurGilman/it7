package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("books_authors")
public class BooksAuthors {
    private Long bookId;
    private Long authorId;
}
