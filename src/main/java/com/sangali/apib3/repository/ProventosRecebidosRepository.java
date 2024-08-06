package com.sangali.apib3.repository;

import com.sangali.apib3.entity.ProventosRecebidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ProventosRecebidosRepository extends JpaRepository<ProventosRecebidos, Long> {

    @Query(value = "SELECT (SUM(p.valorOperacao)) FROM ProventosRecebidos p WHERE p.produto LIKE :produto% AND p.instituicao  LIKE :instituicao% ")
    BigDecimal consultaProventos(String produto, String instituicao);

}
