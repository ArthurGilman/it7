package ru.itfb.it7.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table("book")
public class Book {
    @Id
    private Long id;
    @Column("title")
    private String title;
    @Column("isbn")
    private String isbn;
    @MappedCollection(idColumn = "book_id")
    Set<BooksAuthors> authors = new HashSet<>();
    @MappedCollection(idColumn = "book_id")
    Set<BooksCategories> categories = new HashSet<>();
    @MappedCollection(idColumn = "book_id")
    List<BookCopy> bookCopies = new ArrayList<>();

    public void addAuthor(@NotNull Author author) {
        authors.add(new BooksAuthors(author.getId()));
    }

    public void addCategory(@NotNull Category category) {
        categories.add(new BooksCategories(category.getId()));
    }
    public void addBookCopy(@NotNull BookCopy bookCopy) {
        bookCopies.add(bookCopy);
    }
    public void deleteBookCopy(@NotNull BookCopy bookCopy) {
        bookCopies.remove(bookCopy);
    }

    public void deleteCategory(@NotNull Category category) {
        categories.remove(new BooksCategories(category.getId()));
    }

    public void deleteAuthor(@NotNull Author author) {
        authors.remove(new BooksAuthors(author.getId()));
    }
}
