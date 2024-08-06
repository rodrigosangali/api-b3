package com.sangali.apib3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "current_price")
@Entity(name = "CurrentPrice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "produto")
public class CurrentPrice {

    @Id
    private String produto;

    private BigDecimal valorOperacao;

    private String moeda;
}
