package com.pb.ProjetoGrupo2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderedProductFormDTO {

    @NotNull(message = "O campo orderedQuantity não pode ser nulo")
    private int orderedQuantity;
    @NotNull(message = "O campo productId não pode ser nulo")
    private Long productId;

}
