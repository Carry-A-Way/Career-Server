package com.example.career.domain.payment.Repository;

import com.example.career.domain.payment.Entity.PurchaseUsed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseUsedRepository extends JpaRepository<PurchaseUsed,Long> {
}
