package com.pb.ProjetoGrupo2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderFormDTO {

    @NotNull(message = "O campo userId não pode ser nulo")
    @NotEmpty(message = "O campo userId não pode ser vazio")
    @NotBlank(message = "O campo userId não pode estar em branco")
    private Long userId;

}
