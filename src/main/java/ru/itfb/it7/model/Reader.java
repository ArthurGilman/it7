package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("reader")
public class Reader {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String ticketNumber;
}
