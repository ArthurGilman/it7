package ru.itfb.it7.dto.request.create;

import lombok.*;
import ru.itfb.it7.model.BookCopy;
import ru.itfb.it7.model.BooksAuthors;
import ru.itfb.it7.model.BooksCategories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class BookCreateRequest {
    private String title;
    private String isbn;
    private Set<BooksAuthors> authors = new HashSet<>();
    private Set<BooksCategories> categories = new HashSet<>();
}
