package ru.itfb.it7.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReaderRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String ticketNumber;
}
