package com.sangali.apib3.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sangali.apib3.utils.CustomLocalDateDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class BrokerageTransactionDTO {
    @JsonProperty("Date")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class) // Usa o deserializer personalizado
    private LocalDate dataOperacao;

    @JsonProperty("Action")
    private String tipoEvento;

    @JsonProperty("Symbol")
    private String produto;

    @JsonProperty("Description")
    private String descricao;

    @JsonProperty("Quantity")
    private String quantidade; // Pode ser null ou vazio, então use String

    @JsonProperty("Price")
    private String preco; // Pode ser null ou vazio, então use String

    @JsonProperty("Fees & Comm")
    private String taxas; // Pode ser null ou vazio, então use String

    @JsonProperty("Amount")
    private BigDecimal valorOperacao; // Use BigDecimal para valores monetários

    @JsonSetter("Amount")
    public void setValorOperacao(String valorOperacao) {
        // Remover $ e vírgulas antes de converter para BigDecimal
        if(!valorOperacao.isBlank()){
            this.valorOperacao = new BigDecimal(valorOperacao.replace("$", "").replace(",", ""));
        } else {
            this.valorOperacao = BigDecimal.ZERO;
        }

    }

    @JsonSetter("Price")
    public void setPreco(String preco) {
        // Verificar se a string está vazia ou nula e, em caso afirmativo, definir como BigDecimal.ZERO
        if (!preco.isBlank()) {
            this.preco = String.valueOf(new BigDecimal(preco.replace("$", "").replace(",", "")));
        }else {
            this.preco = String.valueOf(BigDecimal.ZERO);
        }
    }
    @JsonSetter("Quantity")
    public void setQuantidade(String quantidade) {
        // Verificar se a string está vazia ou nula e, em caso afirmativo, definir como BigDecimal.ZERO
        if (Objects.isNull(quantidade) || quantidade.isBlank()) {
            this.quantidade = String.valueOf(BigDecimal.ZERO);
        }else {
            this.quantidade = quantidade;
        }
    }
}
