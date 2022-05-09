package com.pb.ProjetoGrupo2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginFormDTO {

    private String email;
    private String password;

    public UsernamePasswordAuthenticationToken encrypt() {
        return new UsernamePasswordAuthenticationToken (email, password);
    }
}
