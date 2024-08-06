package com.sangali.apib3.service;

import com.sangali.apib3.entity.ExtratoNegociacao;
import com.sangali.apib3.entity.Split;
import com.sangali.apib3.model.enums.InstituicaoEnum;
import com.sangali.apib3.repository.ExtratoNegociacaoRepository;
import com.sangali.apib3.repository.SplitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SplitService {

    @Autowired
    SplitRepository splitRepository;

    @Autowired
    ExtratoNegociacaoRepository extratoNegociacaoRepository;

    public void executarSplitB3(){
        // Busca os produtos
        List<Split> listaProdutos = splitRepository.findAll();

        // Realizar a consulta na tabela de split para processamento
        listaProdutos.forEach( p->{
            Map<String, Object> dataMultiplo =  splitRepository.obtemDataMultiploSplit(p.getProduto());

            Integer quantidadeAcoes = extratoNegociacaoRepository.obtemQuantidadeAtivos(p.getProduto(), "CLEAR%", (LocalDate) dataMultiplo.get("dataSplit"));

            if (Objects.nonNull(quantidadeAcoes) && p.getProcess().equals(false)){
                Integer quantidade = (quantidadeAcoes / 2) * (Integer) dataMultiplo.get("multiplo");

                ExtratoNegociacao extratoNegociacao = ExtratoNegociacao.builder()
                        .codProduto(p.getProduto())
                        .dataNegocio((LocalDate) dataMultiplo.get("dataSplit"))
                        .instituicao(InstituicaoEnum.CLEAR.getInstituicao())
                        .precoUnitario(BigDecimal.ZERO)
                        .valorOperacao(BigDecimal.ZERO)
                        .quantidade(quantidade)
                        .tipoMovimentacao("Compra")
                        .build();

                extratoNegociacaoRepository.save(extratoNegociacao);

                splitRepository.atualizarProcess( true, p.getId());

            }


        });
    }

    public void executarSplitStock() {



    }
}
