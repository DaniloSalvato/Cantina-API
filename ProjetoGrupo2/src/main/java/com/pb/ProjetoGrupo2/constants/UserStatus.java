package com.pb.ProjetoGrupo2.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    UserStatus(String value) {
        this.value = value;
    }

    private String value;

    @JsonCreator
    public static UserStatus fromValue(String value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
