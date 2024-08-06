package ru.itfb.it7.dto.request.create;


import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class ReaderTicketCreateRequest {
    @NotNull
    private Long id;
}
