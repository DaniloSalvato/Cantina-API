package com.pb.ProjetoGrupo2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pb.ProjetoGrupo2.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR")
    private LocalDate localDate;
    private UserOrderDTO user;
    private BigDecimal totalPrice;
    private OrderStatus status;

}
