package com.pb.ProjetoGrupo2.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "daily_report")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmountSold = new BigDecimal(0);
    private LocalDate generateDate = LocalDate.now();

}
