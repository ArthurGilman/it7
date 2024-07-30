package ru.itfb.it7.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("rack")
public class Rack {
    @Id
    private Long id;
    private String location;
    @MappedCollection(idColumn = "rack_id")
    private Set<Shelf> shelves;
}

