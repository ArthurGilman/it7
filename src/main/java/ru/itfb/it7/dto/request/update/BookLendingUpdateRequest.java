package ru.itfb.it7.dto.request.update;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class BookLendingUpdateRequest {
    private Long id;
    private Long copyId;
    private Long ticketId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lendDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnDate;
}
