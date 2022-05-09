package com.pb.ProjetoGrupo2.service;

import com.pb.ProjetoGrupo2.dto.*;
import com.pb.ProjetoGrupo2.entities.User;
import com.pb.ProjetoGrupo2.repository.OrderRepository;
import com.pb.ProjetoGrupo2.repository.OrderedProductRepository;
import com.pb.ProjetoGrupo2.repository.ProductRepository;
import com.pb.ProjetoGrupo2.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderedProductRepository orderedProductRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO postUser(UserFormDTO userFormDto) {
        User user = new User();
        user.setName(userFormDto.getName());
        user.setEmail(userFormDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userFormDto.getPassword()));
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserDTO> userDTOList =
                users.stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(userDTOList, pageable, userDTOList.size());
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return modelMapper.map(optionalUser.get(), UserDTO.class);
        }
        return null;
    }

    @Override
    public UserDTO putUser(Long id, UpdatedUserFormDTO updatedUserFormDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            updatedUser.setName(updatedUserFormDTO.getName());
            updatedUser.setStatus(updatedUserFormDTO.getStatus());
            userRepository.save(updatedUser);
            return modelMapper.map(updatedUser, UserDTO.class);
        }
        return null;
    }
}

