package ru.itfb.it7.dto.request.create;

import lombok.*;
import org.springframework.data.annotation.Id;
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
public class BookDisposalRequest {
    private Long copyId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String reason;
}
