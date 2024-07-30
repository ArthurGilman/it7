package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("book")
public class Book {
    @Id
    private Long id;
    private String title;
    private String isbn;
}
