package com.sangali.apib3.controller;

import com.sangali.apib3.service.TransactionsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;


    @PostMapping
    @Transactional
    public void cadastrarTransactions() throws IOException {

        transactionsService.cadastrarTransacao();

    }
}
