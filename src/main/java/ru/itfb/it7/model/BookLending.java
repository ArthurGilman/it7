package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("book_lending")
public class BookLending {
    @Id
    private Long id;
    @Column("copy_id")
    private Long copyId;
    @Column("ticket_id")
    private Long ticketId;
    @Column("lend_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lendDate;
    @Column("due_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;
    @Column("return_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnDate;
}
