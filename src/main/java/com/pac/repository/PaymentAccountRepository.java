package com.pac.repository;

import com.pac.domain.PaymentAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PaymentAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Long> {

    @Query("select paymentAccount from PaymentAccount paymentAccount where paymentAccount.owner.login = ?#{principal.username}")
    List<PaymentAccount> findByOwnerIsCurrentUser();
}
