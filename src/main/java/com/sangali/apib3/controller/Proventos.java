package com.sangali.apib3.controller;

import com.sangali.apib3.entity.ProventosRecebidos;
import com.sangali.apib3.model.LinhaExcel;
import com.sangali.apib3.repository.ProventosRecebidosRepository;
import com.sangali.apib3.service.RealExcelFileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("proventos")
public class Proventos {
    @Autowired
    ProventosRecebidosRepository proventosRecebidosRepository;

    RealExcelFileService realExcelFileService;

    @PostMapping(value = "/recebidos")
    @Transactional
    public void cadastrarProventosRecebidos() throws IOException {
        realExcelFileService = new RealExcelFileService();
        List<LinhaExcel> linhasExcel = realExcelFileService.lerExcelProventosRecebidos();

        linhasExcel.forEach( linha -> {
            proventosRecebidosRepository.save(new ProventosRecebidos(linha));
        });




    }

}
