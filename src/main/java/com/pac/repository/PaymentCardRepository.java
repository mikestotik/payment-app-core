package com.pac.repository;

import com.pac.domain.PaymentCard;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PaymentCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {

    @Query("select paymentCard from PaymentCard paymentCard where paymentCard.owner.login = ?#{principal.username}")
    List<PaymentCard> findByOwnerIsCurrentUser();
}
