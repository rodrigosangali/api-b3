package com.sangali.apib3.entity;

import com.sangali.apib3.model.SplitRequest;
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

    private Boolean process;

    public Split(SplitRequest splitRequest){
        this.produto = splitRequest.getProduto();
        this.dataSplit = splitRequest.getDataSplit();
        this.multiplo = splitRequest.getMultiplo();
        this.process = splitRequest.getProcess();

    }

}
