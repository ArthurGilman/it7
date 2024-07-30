package ru.itfb.it7.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("shelf")
public class Shelf {
    @Id
    private Long id;
    private String position;
    private Integer rackId;
    private Integer capacity;
}
