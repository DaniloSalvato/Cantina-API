package com.pb.ProjetoGrupo2.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Type {
    FRITO("frito"),
    ASSADO("assado"),
    BEBIDA("bebida"),
    DOCE("doce"),
    NATURAL("natural");

    Type(String value) {
        this.value = value;
    }

    private String value;

    @JsonCreator
    public static Type fromValue(String value) {
        for (Type type : Type.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
