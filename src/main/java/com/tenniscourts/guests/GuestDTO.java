package com.tenniscourts.guests;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GuestDTO {
    private Long id;
    private String name;
}
