package com.sangali.apib3.service;

import com.sangali.apib3.entity.Transactions;
import com.sangali.apib3.model.LinhaExcel;
import com.sangali.apib3.repository.TransactionsRepository;
import com.sangali.apib3.utils.SHA1HasGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
@Service
public class TransactionsService {
    @Autowired
    private TransactionsRepository transactionsRepository;

    RealExcelFileService realExcelFileService;

    public  void cadastrarTransacao() throws IOException {

        realExcelFileService = new RealExcelFileService();
        // Le o execel na pasta
        List<LinhaExcel> linhasExcel = realExcelFileService.lerExcelTransactions();

        // Verifica se há duplicidade pelo hash
        Iterator<LinhaExcel> iterator = linhasExcel.iterator();
        while (iterator.hasNext()){

            LinhaExcel linha = iterator.next();

            Boolean transacao = transactionsRepository.existsByHash(
                    SHA1HasGenerator.generatorSHA1Hash(linha.getDataOperacao()
                            + linha.getTipoMovimentacao()
                            + linha.getProduto()
                            + linha.getQuantidade()
                            + linha.getPrecoUnitario()
                            + linha.getValorOperacao()));
            if (transacao) {
                iterator.remove();
            }
        }

        // Salva as linhas no banco de dados
        linhasExcel.forEach( linha -> {
            transactionsRepository.save(new Transactions(linha));
        });

        System.out.println("Quantidade de linhas inseridas: " + linhasExcel.size());
    }

}
