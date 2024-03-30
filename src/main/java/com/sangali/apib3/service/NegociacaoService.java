package com.sangali.apib3.service;

import com.sangali.apib3.model.Negociacao;
import com.sangali.apib3.repository.CurrentPriceRepository;
import com.sangali.apib3.repository.ExtratoNegociacaoRepository;
import com.sangali.apib3.repository.ProventosRecebidosRepository;
import com.sangali.apib3.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.el.lang.ELArithmetic.divide;

@Service
public class NegociacaoService {

    @Autowired
    private ExtratoNegociacaoRepository extratoNegociacaoRepository;

    @Autowired
    ProventosRecebidosRepository proventosRecebidosRepository;
    @Autowired
    private CurrentPriceRepository currentPriceRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    public List<Negociacao> consultaResumoAtivo(){
        // Busca os produtos
        List<String> listaProdutos = extratoNegociacaoRepository.consultaProdutos();

        // Busca Negociacao
        List<Negociacao> listaNegociados = new ArrayList<>();

        // Busca os produtos de transactions
        List<String> listaProdutoTransaciton = transactionsRepository.findProduto();

        // Para cada produto buscar o valor da operacao sumarizado por produto e instituicao
        List<Negociacao> finalListaNegociados = listaNegociados;
        listaProdutos.forEach(p ->   {
            Negociacao negociacao = new Negociacao();
            // Obtem valor da operacao subtraindo a compra da venda
            BigDecimal valorOperacao = extratoNegociacaoRepository.sumarizarPorProdutoInstituicao(p, "CLEAR");
            BigDecimal precoMedio = extratoNegociacaoRepository.precoMedioPorProdutoInstituicao(p, "CLEAR");
            Integer quantidade = extratoNegociacaoRepository.quantidadePorProdutoInstituicao(p, "CLEAR");
            BigDecimal totalProventos = proventosRecebidosRepository.consultaProventos(p.substring(0, 5), "CLEAR");
            BigDecimal currentPrice = currentPriceRepository.getCurrentPrice(p, "BRL");

            negociacao.setCodProduto(p);
            negociacao.setValorOperacao(valorOperacao);
            negociacao.setPrecoMedio(precoMedio);
            negociacao.setQuantidade(quantidade);
            negociacao.setTotalProventos(totalProventos);
            negociacao.setPrecoAtual(currentPrice);

            try{
                negociacao.setSaldoAtual(negociacao.getPrecoAtual().multiply(BigDecimal.valueOf(negociacao.getQuantidade())));
                negociacao.setValorizacao(retornaValorizacao(negociacao.getValorOperacao(), negociacao.getSaldoAtual()));

            }catch (Exception e){
                System.out.println(p + "..." + e );
            }

            // Adiciona na lista de negociacao
            finalListaNegociados.add(negociacao);

        });

        // EUA Trata as stock e reit
        listaProdutoTransaciton.forEach(p -> {
            Negociacao negociacao = new Negociacao();
            negociacao.setCodProduto(p);
            Map<String, Object> sumarizadoporProduto = transactionsRepository.obtemQuantidadeProduto(p);
            BigDecimal somaTotalProventos = transactionsRepository.somaTotalProvento(p);
            BigDecimal currentPrice = currentPriceRepository.getCurrentPriceUSD(p, "USD");

            try {

                negociacao.setQuantidade(Integer.parseInt(sumarizadoporProduto.get("somaQuantidade").toString()));
                // Se uma ação foi vendidade faço a soma do valor operacao pela quantidade de ações antes da venda e depois multiplo pela quantidade atual
                if(Integer.parseInt(sumarizadoporProduto.get("somaQuantidadeVendida").toString()) > 0){
                    negociacao.setValorOperacao((BigDecimal) divide(sumarizadoporProduto.get("somaValorOperacao"),(negociacao.getQuantidade() + Integer.parseInt(sumarizadoporProduto.get("somaQuantidadeVendida").toString()))));
                    negociacao.setValorOperacao(negociacao.getValorOperacao().multiply(BigDecimal.valueOf(negociacao.getQuantidade())));
                }else{
                    negociacao.setValorOperacao((BigDecimal) sumarizadoporProduto.get("somaValorOperacao"));
                }


                // Transformando o numero em positivo, vem negativo sempre
                negociacao.setValorOperacao(negociacao.getValorOperacao().multiply(BigDecimal.valueOf(-1)));
                negociacao.setValorOperacao(negociacao.getValorOperacao().multiply(BigDecimal.valueOf(4.98)));
                negociacao.setPrecoMedio(negociacao.getValorOperacao().divide(BigDecimal.valueOf(negociacao.getQuantidade()), 4, RoundingMode.HALF_UP));
                negociacao.setTotalProventos(somaTotalProventos);
                currentPrice = currentPrice.multiply(BigDecimal.valueOf(4.98));
                negociacao.setPrecoAtual(currentPrice);
                if (Objects.nonNull(negociacao.getValorOperacao()) && Objects.nonNull(negociacao.getTotalProventos())){
                    negociacao.setTotalProventos(negociacao.getTotalProventos().multiply(BigDecimal.valueOf(4.98)));
                    negociacao.setValorizacaoProventos(retornaPercentual(negociacao.getTotalProventos(), negociacao.getValorOperacao()));
                }

                negociacao.setSaldoAtual(negociacao.getPrecoAtual().multiply(BigDecimal.valueOf(negociacao.getQuantidade())));
                negociacao.setValorizacao(retornaValorizacao(negociacao.getValorOperacao(), negociacao.getSaldoAtual()));

            } catch (Exception e) {
                System.out.println("Erro no produto" + p + " " + e);
            }

            finalListaNegociados.add(negociacao);

        });

        // filtra campos nulos e sem operacao
        listaNegociados = listaNegociados.stream().filter(f -> f.getValorOperacao() != null && (f.getQuantidade() != null && f.getQuantidade() > 0)).collect(Collectors.toList());

        // ordena pelo maior saldo Atual
        listaNegociados =  listaNegociados.stream().sorted(Comparator.comparing(Negociacao::getSaldoAtual).reversed()).collect(Collectors.toList());

        // Obtém o saldo total das copras pelo Saldo Atual
        BigDecimal valorTotalSaldo = listaNegociados.stream().map(Negociacao::getSaldoAtual).reduce(BigDecimal.ZERO, BigDecimal::add);



        // Obtem o percentual que o ativo representa na carteira completa.
        listaNegociados.forEach(negociacao -> {
            if (Objects.nonNull(negociacao.getSaldoAtual())) {
                negociacao.setPercentualCarteira(retornaPercentual(negociacao.getSaldoAtual(), valorTotalSaldo));

            }

            if (Objects.nonNull(negociacao.getValorizacao()) && Objects.nonNull(negociacao.getTotalProventos())){
                negociacao.setValorizacaoProventos(retornaPercentual(negociacao.getTotalProventos(), negociacao.getValorOperacao()));
                negociacao.setTotalValorizacao(negociacao.getValorizacao().add(negociacao.getValorizacaoProventos()));
            } else if (Objects.nonNull(negociacao.getValorizacao())) {
                negociacao.setTotalValorizacao(negociacao.getValorizacao().add(BigDecimal.ZERO));
            }
        });

        BigDecimal valorTotalOperacao = listaNegociados.stream().map(Negociacao::getValorOperacao).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Valor Total Saldo: " + valorTotalSaldo);
        System.out.println("Valor Total da Operacao: " + valorTotalOperacao);

        return listaNegociados;
    }

    public BigDecimal retornaValorizacao(BigDecimal valorOperacao, BigDecimal saldoAtual){

        // Calculando a diferença entre os valores
        BigDecimal diferenca = saldoAtual.subtract(valorOperacao);
        // Calculando a diferença em porcentagem
        BigDecimal porcentagem = diferenca.divide(valorOperacao, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        return porcentagem;
    }

    public BigDecimal retornaPercentual(BigDecimal saldo, BigDecimal valorOperacao){

        // saldo zerado
        if (saldo.compareTo(BigDecimal.ZERO) == 0 || valorOperacao.compareTo(BigDecimal.ZERO) == 0){
            return BigDecimal.ZERO;
        }

        return saldo.divide(valorOperacao,4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }

}
