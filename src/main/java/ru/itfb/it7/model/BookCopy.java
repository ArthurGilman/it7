package ru.itfb.it7.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("book_copy")
public class BookCopy {
    @Id
    private Long id;
    private Integer bookId;
    private Integer shelfId;
    private String status;
}
