package com.pac.repository;

import com.pac.domain.Payment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Payment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select payment from Payment payment where payment.owner.login = ?#{principal.username}")
    List<Payment> findByOwnerIsCurrentUser();
}
