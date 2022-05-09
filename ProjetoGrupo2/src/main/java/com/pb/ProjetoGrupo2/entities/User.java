package com.pb.ProjetoGrupo2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pb.ProjetoGrupo2.constants.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Perfil> perfis = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return this.perfis;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override //verifica se a conta esta expirada
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override //verifica se a conta não esta bloqueada
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override //verifica se a credencial não esta expirada
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override//verifica se esta hgabilitada
    public boolean isEnabled() {
        return true;
    }
}
