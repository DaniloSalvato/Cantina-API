package com.pb.ProjetoGrupo2.controller;

import com.pb.ProjetoGrupo2.config.security.TokenService;
import com.pb.ProjetoGrupo2.dto.LoginFormDTO;
import com.pb.ProjetoGrupo2.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Profile("prod")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginFormDTO loginFormDto) {
        UsernamePasswordAuthenticationToken loginData = loginFormDto.encrypt();

        try{
            Authentication authentication = authManager.authenticate(loginData);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token,"Bearer"));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }

    }
}
