package com.sangali.apib3.controller;

import com.sangali.apib3.entity.ExtratoNegociacao;
import com.sangali.apib3.entity.Split;
import com.sangali.apib3.model.LinhaExcel;
import com.sangali.apib3.model.Negociacao;
import com.sangali.apib3.model.SplitRequest;
import com.sangali.apib3.repository.ExtratoNegociacaoRepository;

import com.sangali.apib3.repository.SplitRepository;
import com.sangali.apib3.service.NegociacaoService;
import com.sangali.apib3.service.RealExcelFileService;
import com.sangali.apib3.service.SplitService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("extrato")
public class Extrato {

    @Autowired
    ExtratoNegociacaoRepository extratoNegociacaoRepository;

    @Autowired
    SplitRepository splitRepository;

    RealExcelFileService realExcelFileService;
    @Autowired
    private NegociacaoService negociacaoService;

    @Autowired
    private SplitService splitService;



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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/negociacao/resumo")
    public List<Negociacao> consultarResumoProdutos() {

        return negociacaoService.consultaResumoAtivo() ;
    }

    @PostMapping(value = "/split")
    @Transactional
    public void splitAtivos(@RequestBody SplitRequest splitRequest) {

        splitRepository.save(new Split(splitRequest));

    }

    @PostMapping(value= "/split/b3/processing")
    @Transactional
    public void processarSplit(){

        splitService.executarSplitB3();

    }

    @PostMapping(value= "/split/processing")
    @Transactional
    public void processarSplitStock(){

        splitService.executarSplitStock();

    }

}
