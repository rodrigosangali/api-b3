package com.sangali.apib3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sangali.apib3.constant.Produto;
import com.sangali.apib3.model.BrokerageTransactionDTO;
import com.sangali.apib3.model.TransactionsSummaryDTO;
import com.sangali.apib3.utils.SHA1HasGenerator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class ReadJsonService {

    private static final String DIRECTORY_PATH = "D:\\WorkspaceJava\\api-b3\\transactions";

    List<BrokerageTransactionDTO> brokerageTransactionList = new ArrayList<>();

    Map <String,BrokerageTransactionDTO> listTransaction = new HashMap<>();

    private static String totalTransaction = "";

    public Map<String,BrokerageTransactionDTO> lerArquivoJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Registra o módulo para suporte a Java 8 Date/Time API

        //Cria o diretório
        File diretorio = new File(DIRECTORY_PATH);

        //Filtra arquivos com extensão json
        List<File> arquivos = Arrays.stream(diretorio.listFiles()).filter(
                file -> file.isFile() && file.getName().endsWith(".json")).collect(Collectors.toList());

        //Para cada arquivo json, faz a leitura
        arquivos.stream().forEach( arquivo -> {
            try{
                TransactionsSummaryDTO transactionsDTO = objectMapper.readValue(arquivo, TransactionsSummaryDTO.class);
                totalTransaction = transactionsDTO.getTotalTransacoes();
                brokerageTransactionList.addAll(transactionsDTO.getTransacoesCorretagem());

            }catch (IOException e){
                e.printStackTrace();
            }
        });

        // Para alguns casos valoriza o produto
        brokerageTransactionList.forEach( transaction ->{
            if(transaction.getProduto().isEmpty()){
                transaction.setProduto(pegaDescricao(transaction.getDescricao()));
            }

            String key = SHA1HasGenerator.generatorSHA1Hash(
                    transaction.getDataOperacao()
                            + transaction.getTipoEvento()
                            + transaction.getProduto()
                            + transaction.getDescricao()
                            + transaction.getTaxas()
                            + transaction.getQuantidade()
                            + transaction.getPreco());

            // Soma os valores se a chave já existir
            listTransaction.merge(key, transaction, (existingTransaction, newTransaction) -> {
                BigDecimal amount = existingTransaction.getValorOperacao().add(newTransaction.getValorOperacao());
                existingTransaction.setValorOperacao(String.valueOf(amount));
                return existingTransaction;
            });

        });

        // Calcula o total das transações
        BigDecimal totalTransactionsAmount = listTransaction.values().stream()
                .map(BrokerageTransactionDTO::getValorOperacao)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Confere os valores totais
        BigDecimal totalTransactionAmount = new BigDecimal(totalTransaction.replace("$", "").replace(",", ""));

        if (totalTransactionAmount.compareTo(totalTransactionsAmount) != 0) {
            throw new RuntimeException("Total das transações não conferem!");
        }

        return listTransaction;
    }

    private String pegaDescricao(String descricao) {

        for(Produto produto : Produto.values()) {
           if(descricao.contains(produto.getProduto())){
               return produto.getProduto();
           }
        }
        return "null";
    }
}
