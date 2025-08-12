package com.CMS.CMS.repositories;

import com.CMS.CMS.models.Card;
import com.CMS.CMS.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,UUID> {
    List<Transaction> findByCard(Card card);
}
