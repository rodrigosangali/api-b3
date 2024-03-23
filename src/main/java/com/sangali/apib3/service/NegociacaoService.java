package com.sangali.apib3.service;

import com.sangali.apib3.model.Negociacao;
import com.sangali.apib3.repository.ExtratoNegociacaoRepository;
import com.sangali.apib3.repository.ProventosRecebidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
public class NegociacaoService {

    @Autowired
    private ExtratoNegociacaoRepository extratoNegociacaoRepository;

    @Autowired
    ProventosRecebidosRepository proventosRecebidosRepository;

    public List<Negociacao> consultaResumoAtivo(){

        List<String> listaProdutos = extratoNegociacaoRepository.consultaProdutos();
        List<Negociacao> listaNegociados = new ArrayList<>();

        // Para cada produto buscar o valor da operacao sumarizado por produto e instituicao
        List<Negociacao> finalListaNegociados = listaNegociados;
        listaProdutos.forEach(p ->   {
            Negociacao negociacao = new Negociacao();
            // Obtem valor da operacao subtraindo a compra da venda
            BigDecimal valorOperacao = extratoNegociacaoRepository.sumarizarPorProdutoInstituicao(p, "CLEAR");
            BigDecimal precoMedio = extratoNegociacaoRepository.precoMedioPorProdutoInstituicao(p, "CLEAR");
            Integer quantidade = extratoNegociacaoRepository.quantidadePorProdutoInstituicao(p, "CLEAR");
            BigDecimal totalProventos = proventosRecebidosRepository.consultaProventos(p.substring(0, 3), "CLEAR");

            negociacao.setCodProduto(p);
            negociacao.setValorOperacao(valorOperacao);
            negociacao.setPrecoMedio(precoMedio);
            negociacao.setQuantidade(quantidade);
            negociacao.setTotalProventos(totalProventos);

            // Adiciona na lista de negociacao
            finalListaNegociados.add(negociacao);

        });
        // filtra campos nulos e sem operacao
        listaNegociados = listaNegociados.stream().filter(f -> f.getValorOperacao() != null && (f.getQuantidade() != null && f.getQuantidade() > 0)).collect(Collectors.toList());
        // ordena pelo maior valor da operacao
        listaNegociados =  listaNegociados.stream().sorted(Comparator.comparing(Negociacao::getValorOperacao).reversed()).collect(Collectors.toList());

        return listaNegociados;
    }

}
