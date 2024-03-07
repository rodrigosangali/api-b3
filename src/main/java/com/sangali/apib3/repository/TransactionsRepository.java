package com.sangali.apib3.repository;


import com.sangali.apib3.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
}
