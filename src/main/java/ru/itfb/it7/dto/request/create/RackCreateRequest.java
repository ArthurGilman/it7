package ru.itfb.it7.dto.request.create;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class RackCreateRequest {
    private String location;
}
