package com.sangali.apib3.controller;

import com.sangali.apib3.entity.ExtratoNegociacao;
import com.sangali.apib3.model.LinhaExcel;
import com.sangali.apib3.repository.ExtratoNegociacaoRepository;

import com.sangali.apib3.service.RealExcelFileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/negociacao")
    public List<ExtratoNegociacao> consultarExtratoNegociacao() {
        return extratoNegociacaoRepository.findAll();
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/negociacao/{produto}")
    public List<ExtratoNegociacao> consultarNegociacoesPorAcao(@PathVariable String produto) {
        return extratoNegociacaoRepository.findByCodProduto(produto);
    }

    // Mesma consulta de cima usando JP
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/negociacao/JPQL/{produtoJPQL}")
    public List<ExtratoNegociacao> consultarNegociacoesPorAcaoJPQL(@PathVariable String produtoJPQL) {

        return extratoNegociacaoRepository.procuraPorProduto(produtoJPQL);
    }
}
