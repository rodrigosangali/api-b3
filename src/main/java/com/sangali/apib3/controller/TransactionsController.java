package com.sangali.apib3.controller;

import com.sangali.apib3.entity.Transactions;
import com.sangali.apib3.model.LinhaExcel;
import com.sangali.apib3.repository.TransactionsRepository;
import com.sangali.apib3.service.RealExcelFileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionsController {

    @Autowired
    TransactionsRepository transactionsRepository;

    RealExcelFileService realExcelFileService;

    @PostMapping
    @Transactional
    public void cadastrarTransactions() throws IOException {
        realExcelFileService = new RealExcelFileService();
        List<LinhaExcel> linhasExcel = realExcelFileService.lerExcelExtratoNegociacao();

        linhasExcel.forEach( linha -> {
            transactionsRepository.save(new Transactions(linha));
        });
    }
}
