package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("books_categories")
public class BooksCategories {
    @Column("category_id")
    private Long categoryId;
}
