package com.sangali.apib3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sangali.apib3.constant.Produto;
import com.sangali.apib3.model.BrokerageTransactionDTO;
import com.sangali.apib3.model.TransactionsSummaryDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReadJsonService {

    private static final String DIRECTORY_PATH = "D:\\WorkspaceJava\\api-b3\\transactions";

    public List<BrokerageTransactionDTO> lerArquivoJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Registra o módulo para suporte a Java 8 Date/Time API

        //Cria o diretório
        File diretorio = new File(DIRECTORY_PATH);

        //Filtra arquivos com extensão json
        List<File> arquivos = Arrays.stream(diretorio.listFiles()).filter(
                file -> file.isFile() && file.getName().endsWith(".json")).collect(Collectors.toList());

        // Lista para armazenar os resultados
        List<BrokerageTransactionDTO> brokerageTransactionList = new ArrayList<>();

        //Para cada arquivo json, faz a leitura
        arquivos.stream().forEach( arquivo -> {
            try{
                TransactionsSummaryDTO transactionsDTO = objectMapper.readValue(arquivo, TransactionsSummaryDTO.class);

                brokerageTransactionList.addAll(transactionsDTO.getTransacoesCorretagem());

            }catch (IOException e){
                e.printStackTrace();
            }

        });

        // Para alguns casos valoriza o produto
        brokerageTransactionList.forEach( t ->{
            if(t.getProduto().isEmpty()){
                t.setProduto(pegaDescricao(t.getDescricao()));

            }
        });

        return brokerageTransactionList;
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
