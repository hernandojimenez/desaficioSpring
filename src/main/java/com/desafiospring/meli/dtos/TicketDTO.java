package com.desafiospring.meli.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private int id;
    private List<ArticleDTO> articles;
    private Float total;
    private int clientId;
}
