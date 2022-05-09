package com.pb.ProjetoGrupo2.services;

import com.pb.ProjetoGrupo2.builder.UserBuilder;
import com.pb.ProjetoGrupo2.config.validation.ObjectNotFoundException;
import com.pb.ProjetoGrupo2.dto.UpdatedUserFormDTO;
import com.pb.ProjetoGrupo2.dto.UserDTO;
import com.pb.ProjetoGrupo2.dto.UserFormDTO;
import com.pb.ProjetoGrupo2.entities.User;
import com.pb.ProjetoGrupo2.repository.UserRepository;
import com.pb.ProjetoGrupo2.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for user Service")
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository repository;

    @Test
    @DisplayName("Save user")
    public void saveUser() {
        User user = UserBuilder.getUser();

        when(this.repository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = this.userService.postUser(UserBuilder.getUserFormDTO());
        userDTO.setId(1L);

        assertThat(userDTO.getId()).isEqualTo(user.getId());
        assertThat(userDTO.getName()).isEqualTo(user.getName());
        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    @DisplayName("List all user")
    public void listUser() {
        User user = UserBuilder.getUser();

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<User> users = Arrays.asList(user);
        Page<User> page = new PageImpl<>(users, pageRequest, 1);

        when(this.repository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<UserDTO> pageUserDTO = this.userService.getAllUsers(pageRequest);

        assertThat(pageUserDTO.getContent()).hasSize(1);
        assertThat(pageUserDTO.getTotalPages()).isEqualTo(1);
        assertThat(pageUserDTO.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("FindById user")
    public void findByIdUser() {
        User user = UserBuilder.getUser();

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(user));

        UserDTO userDTO = this.userService.getUserById(user.getId());

        assertThat(userDTO.getId()).isNotNull();
        assertThat(userDTO.getName()).isEqualTo(user.getName());
        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    @DisplayName("findById user not found")
    public void findByIdUser_NotFound() {
        User user = UserBuilder.getUser();

        when(this.repository.findById(anyLong())).thenReturn(null);

        assertThatThrownBy(() -> this.userService.getUserById(user.getId()));
    }

    @Test
    @DisplayName("Update user")
    public void updateUser() {
        User user = UserBuilder.getUser();
        UpdatedUserFormDTO updatedUserFormDTO = UserBuilder.getUpdatedUserFormDTO();
        UserFormDTO userFormDTO = UserBuilder.getUserFormDTO();
        userFormDTO.setName("Aluno");

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(user));
        when(this.repository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = this.userService.putUser(user.getId(), updatedUserFormDTO);

        assertThat(userDTO.getId()).isNotNull();
        assertThat(userDTO.getName()).isEqualTo(userFormDTO.getName());
        assertThat(userDTO.getEmail()).isEqualTo(userFormDTO.getEmail());

    }

    @Test
    @DisplayName("Update user not found")
    public void updateUser_NotFound() {
        User user = UserBuilder.getUser();
        UpdatedUserFormDTO updatedUserFormDTO = UserBuilder.getUpdatedUserFormDTO();

        when(this.repository.findById(anyLong())).thenReturn(null);

        assertThatThrownBy(() -> this.userService.putUser(user.getId(), UserBuilder.getUpdatedUserFormDTO()));
    }

}