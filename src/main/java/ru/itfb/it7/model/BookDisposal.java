package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("book_disposal")
public class BookDisposal {
    @Id
    private Long id;
    private Long copyId;
    private Long ticketId;
    private LocalDate lendDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
