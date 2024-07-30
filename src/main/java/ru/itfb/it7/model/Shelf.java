package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("shelf")
public class Shelf {
    @Id
    private Long id;
    private String position;
    @Column("rack_id")
    private Long rackId;
    private Integer capacity;
}
