package com.example.career.domain.payment.Repository;

import com.example.career.domain.payment.Entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
