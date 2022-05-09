package com.pb.ProjetoGrupo2.dto;

import com.pb.ProjetoGrupo2.config.validation.InstitutionalEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFormDTO {

    @NotNull(message = "O campo name não pode ser nulo")
    @NotEmpty(message = "O campo name não pode ser vazio")
    @NotBlank(message = "O campo name não pode estar em branco")
    private String name;
    @NotNull(message = "O campo email não pode ser nulo")
    @NotEmpty(message = "O campo email não pode ser vazio")
    @NotBlank(message = "O campo email não pode estar em branco")
    @InstitutionalEmail
    @Email
    private String email;
    @NotNull(message = "O campo password não pode ser nulo")
    @NotEmpty(message = "O campo password não pode ser vazio")
    @NotBlank(message = "O campo password não pode estar em branco")
    private String password;

}
