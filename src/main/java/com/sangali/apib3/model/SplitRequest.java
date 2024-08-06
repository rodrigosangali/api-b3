package com.sangali.apib3.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class SplitRequest{

    private String produto;

    private LocalDate dataSplit;

    private Integer multiplo;

    private Boolean process;
}
