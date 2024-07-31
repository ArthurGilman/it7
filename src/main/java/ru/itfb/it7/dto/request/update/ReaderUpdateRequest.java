package ru.itfb.it7.dto.request.update;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
public class ReaderUpdateRequest {
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private String ticketNumber;
}
