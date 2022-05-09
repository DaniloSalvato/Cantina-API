package com.pb.ProjetoGrupo2.builder;

import com.pb.ProjetoGrupo2.constants.UserStatus;
import com.pb.ProjetoGrupo2.dto.UpdatedUserFormDTO;
import com.pb.ProjetoGrupo2.dto.UserDTO;
import com.pb.ProjetoGrupo2.dto.UserFormDTO;
import com.pb.ProjetoGrupo2.dto.UserOrderDTO;
import com.pb.ProjetoGrupo2.entities.User;

public class UserBuilder {

    public static User getUser() {

        User user = new User();
        user.setId(1L);
        user.setName("Aluno");
        user.setStatus(UserStatus.ACTIVE);
        user.setEmail("aluno@fatec.sp.gov.br");
        user.setPassword("aluno");
        return user;
    }

    public static UserDTO getUserDto() {
        UserDTO userDto = new UserDTO();
        userDto.setId(1L);
        userDto.setName("Aluno");
        userDto.setStatus(UserStatus.ACTIVE);
        userDto.setEmail("aluno@fatec.sp.gov.br");
        return userDto;
    }

    public static UserDTO getUserDtoTwo() {
        UserDTO userDtoTwo = new UserDTO();
        userDtoTwo.setId(2L);
        userDtoTwo.setName("Aluno 02");
        userDtoTwo.setStatus(UserStatus.ACTIVE);
        userDtoTwo.setEmail("aluno02@fatec.sp.gov.br");
        return userDtoTwo;
    }

    public static UserFormDTO getUserFormDTO() {
        UserFormDTO userFormDto = new UserFormDTO();
        userFormDto.setName("Aluno");
        userFormDto.setEmail("aluno@fatec.sp.gov.br");
        userFormDto.setPassword("aluno");
        return userFormDto;
    }

    public static UpdatedUserFormDTO getUpdatedUserFormDTO(){
        UpdatedUserFormDTO updatedUserFormDTO = new UpdatedUserFormDTO();
        updatedUserFormDTO.setName("Aluno");
        updatedUserFormDTO.setStatus(UserStatus.ACTIVE);
        return updatedUserFormDTO;
    }

    public static UserOrderDTO getUserOrderDTO(){
        UserOrderDTO userOrderDTO = new UserOrderDTO();
        userOrderDTO.setId(1L);
        userOrderDTO.setName("Aluno");
        return userOrderDTO;
    }
}
