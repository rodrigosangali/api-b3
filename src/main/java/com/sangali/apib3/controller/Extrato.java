package com.sangali.apib3.controller;

import com.sangali.apib3.entity.ExtratoNegociacao;
import com.sangali.apib3.model.LinhaExcel;
import com.sangali.apib3.repository.ExtratoNegociacaoRepository;

import com.sangali.apib3.service.RealExcelFileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("extrato")
public class Extrato {

    @Autowired
    ExtratoNegociacaoRepository extratoNegociacaoRepository;

    RealExcelFileService realExcelFileService;

    @PostMapping(value = "/negociacao")
    @Transactional
    public void cadastrarExtratoNegociacao() throws IOException {
        realExcelFileService = new RealExcelFileService();
        List<LinhaExcel> linhasExcel = realExcelFileService.lerExcelExtratoNegociacao();

        linhasExcel.forEach( linha -> {
            extratoNegociacaoRepository.save(new ExtratoNegociacao(linha));
        });
    }
}
