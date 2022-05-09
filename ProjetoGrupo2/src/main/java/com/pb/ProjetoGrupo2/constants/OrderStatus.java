package com.pb.ProjetoGrupo2.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {

    OPEN("open"),
    WITHDRAWN("withdrawn"),
    NOT_WITHDRAWN("not_withdrawn");

    OrderStatus(String value) {
        this.value = value;
    }

    private String value;

    @JsonCreator
    public static OrderStatus fromValue(String value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new RuntimeException("invalid option, you can use Withdrawn and Not_withdrawn");
    }
}
