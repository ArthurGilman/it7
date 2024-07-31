package ru.itfb.it7.dto.request.create;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
public class ReaderCreateRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private String ticketNumber;
}
