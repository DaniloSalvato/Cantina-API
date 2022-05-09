package com.pb.ProjetoGrupo2.dto;

import com.pb.ProjetoGrupo2.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderStatusUpdateFormDTO {

    @NotNull(message = "O campo status não pode ser nulo")
    private OrderStatus status;

}
