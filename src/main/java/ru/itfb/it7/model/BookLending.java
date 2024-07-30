package ru.itfb.it7.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("book_lending")
public class BookLending {
    @Id
    private Long id;
    private Integer copyId;
    private Integer ticketId;
    private LocalDate lendDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
