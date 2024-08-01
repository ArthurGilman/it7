package ru.itfb.it7.dto.request;

import lombok.*;
import ru.itfb.it7.model.ReaderTicket;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class ReaderTicketRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String ticketNumber;
    private ReaderTicket readerTicket;
}
