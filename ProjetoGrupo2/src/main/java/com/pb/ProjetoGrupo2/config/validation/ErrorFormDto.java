package com.pb.ProjetoGrupo2.config.validation;

import lombok.Getter;

@Getter
public class ErrorFormDto {

    private String name;
    private String error;

    public ErrorFormDto(String campo, String error) {
        this.name = campo;
        this.error = error;
    }
}
