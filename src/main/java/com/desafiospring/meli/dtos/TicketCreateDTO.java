package com.desafiospring.meli.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCreateDTO {
    private Integer ticketId;
    private Integer productId;
    private Float total;
    private Integer clientId;
}
