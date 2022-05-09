package com.pb.ProjetoGrupo2.entities;

import com.pb.ProjetoGrupo2.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate purchaseDate;
    private BigDecimal totalPrice = new BigDecimal(0);
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.OPEN;

    public Order(LocalDate localDate, User user){
        this.purchaseDate = localDate;
        this.user = user;
    }

}
