package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "books_authors", schema = "library")
public class BooksAuthors {
    @Column("author_id")
    private Long authorId;
}
