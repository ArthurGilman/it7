package ru.itfb.it7.dto.request.create;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class BookCopyCreateRequest {
    private Long bookId;
    private Long shelfId;
    private String status;
}
