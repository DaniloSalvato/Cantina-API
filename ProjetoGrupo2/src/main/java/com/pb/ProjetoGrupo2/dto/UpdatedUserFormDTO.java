package com.pb.ProjetoGrupo2.dto;

import com.pb.ProjetoGrupo2.constants.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatedUserFormDTO {

    @NotNull(message = "O campo name não pode ser nulo")
    @NotEmpty(message = "O campo name não pode ser vazio")
    @NotBlank(message = "O campo name não pode estar em branco")
    private String name;
    @NotNull(message = "O campo status não pode ser nulo")
    private UserStatus status;

}
