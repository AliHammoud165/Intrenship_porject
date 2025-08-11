package com.CMS.CMS.Repositories;

import com.CMS.CMS.Entities.Card;
import com.CMS.CMS.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction,UUID> {
    List<Transaction> findByCard(Card card);
}
