package com.sangali.apib3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Table(name = "split")
@Entity(name = "Split")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Split {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String produto;

    private LocalDate dataSplit;

    private Integer multiplo;

}
