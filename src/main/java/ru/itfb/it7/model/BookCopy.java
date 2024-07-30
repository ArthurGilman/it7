package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("book_copy")
public class BookCopy {
    @Id
    private Long id;
    private Long bookId;
    private Long shelfId;
    private String status;
}
