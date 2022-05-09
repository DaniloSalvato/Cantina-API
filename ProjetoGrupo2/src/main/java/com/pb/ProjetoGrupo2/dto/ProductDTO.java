package com.pb.ProjetoGrupo2.dto;

import com.pb.ProjetoGrupo2.constants.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {

    private Long id;
    private String name;
    private Type type;
    private BigDecimal unityPrice;
    private int quantity;

}
