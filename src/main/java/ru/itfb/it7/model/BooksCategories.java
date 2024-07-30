package ru.itfb.it7.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("books_categories")
public class BooksCategories {
    private Integer bookId;
    private Integer categoryId;
}
