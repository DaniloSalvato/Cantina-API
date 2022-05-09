package com.pb.ProjetoGrupo2.entities;

import com.pb.ProjetoGrupo2.constants.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "ordered_product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private int orderedQuantity;
    private BigDecimal unityPrice;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;

    public OrderedProduct(String name, Type type, int orderedQuantity, BigDecimal unityPrice, Order order, Product product){
        this.name = name;
        this.type = type;
        this.orderedQuantity = orderedQuantity;
        this.unityPrice = unityPrice;
        this.order = order;
        this.product = product;
    }
}
