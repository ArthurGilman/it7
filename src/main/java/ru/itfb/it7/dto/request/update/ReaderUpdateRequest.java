package ru.itfb.it7.dto.request.update;

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
public class ReaderUpdateRequest {
    @NotNull
    private Long id;
}
