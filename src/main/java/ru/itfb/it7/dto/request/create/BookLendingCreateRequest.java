package ru.itfb.it7.dto.request.create;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class BookLendingCreateRequest {
    private Long copyId;
    private Long ticketId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lendDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnDate;
}
