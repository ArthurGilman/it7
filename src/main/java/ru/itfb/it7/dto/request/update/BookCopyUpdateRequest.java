package ru.itfb.it7.dto.request.update;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class BookCopyUpdateRequest {
    private Long id;
    private Long bookId;
    private Long shelfId;
    private String status;
}
