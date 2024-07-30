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
@Table("reader_ticket")
public class ReaderTicket {
    @Id
    private Long id;
    private Integer readerId;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;
}
