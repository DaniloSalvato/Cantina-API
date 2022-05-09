package com.pb.ProjetoGrupo2.dto;

import com.pb.ProjetoGrupo2.constants.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private Long id;
    private String name;
    private UserStatus status;
    private String email;

}
