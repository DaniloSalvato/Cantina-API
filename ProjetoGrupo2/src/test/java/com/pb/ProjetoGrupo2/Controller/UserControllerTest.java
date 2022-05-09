package com.pb.ProjetoGrupo2.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pb.ProjetoGrupo2.builder.UserBuilder;
import com.pb.ProjetoGrupo2.dto.UpdatedUserFormDTO;
import com.pb.ProjetoGrupo2.dto.UserDTO;
import com.pb.ProjetoGrupo2.entities.User;
import com.pb.ProjetoGrupo2.service.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    void postUser_ShouldReturn_SUCCESS() throws Exception {

        User user = UserBuilder.getUser();
        UserDTO userDto = UserBuilder.getUserDto();

        when(userService.postUser(any())).thenReturn(userDto);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andDo(print());
    }

    @Test
    void getAllUsers_ShouldReturn_SUCCESS() throws Exception {

        List<UserDTO> userDTOList = new ArrayList<>(
                Arrays.asList(UserBuilder.getUserDto(), UserBuilder.getUserDtoTwo())
        );

        PageRequest pageRequest = PageRequest.of(0, 5);

        Page<UserDTO> userDtoPage =
                new PageImpl<>(userDTOList, pageRequest, userDTOList.size());

        when(userService.getAllUsers(any(PageRequest.class))).thenReturn(userDtoPage);

        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].name").value("Aluno"))
                .andExpect(jsonPath("$.content.[1].name").value("Aluno 02"))
                .andDo(print());

    }

    @Test
    void getUserById_ShouldReturn_SUCCESS() throws Exception {

        User user = UserBuilder.getUser();
        UserDTO userDto = UserBuilder.getUserDto();

        when(userService.getUserById(user.getId())).thenReturn(userDto);

        long id = 1;

        mockMvc.perform(get("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andDo(print());
    }

    @Test
    void putUser_ShouldReturn_SUCCESS() throws Exception {

        User user = UserBuilder.getUser();
        UpdatedUserFormDTO updatedUserFormDTO = UserBuilder.getUpdatedUserFormDTO();
        UserDTO userDto = UserBuilder.getUserDto();

        user.setName("Novo usuário");
        updatedUserFormDTO.setName("Novo usuário");
        userDto.setName("Novo usuário");

        when(userService.putUser(anyLong(), any(UpdatedUserFormDTO.class))).thenReturn(userDto);

        mockMvc.perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserFormDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedUserFormDTO.getName()))
                .andDo(print());
    }
}

