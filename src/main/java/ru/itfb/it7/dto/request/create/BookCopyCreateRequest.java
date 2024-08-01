package ru.itfb.it7.dto.request.create;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class BookCopyCreateRequest {
    private Long bookId;
    private Long shelfId;
    private String status;
}
