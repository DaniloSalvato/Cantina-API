package com.pb.ProjetoGrupo2.dto;

import com.pb.ProjetoGrupo2.constants.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductFormDTO {

    @NotNull(message = "O campo name não pode ser nulo")
    @NotEmpty(message = "O campo name não pode ser vazio")
    @NotBlank(message = "O campo name não pode estar em branco")
    private String name;
    @NotNull(message = "O campo type não pode ser nulo")
    private Type type;
    @NotNull(message = "O campo unityPrice não pode ser nulo")
    private BigDecimal unityPrice;
    private int quantity;

}
