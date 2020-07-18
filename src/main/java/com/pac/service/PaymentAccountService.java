package com.pac.service;

import com.pac.service.dto.PaymentAccountDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pac.domain.PaymentAccount}.
 */
public interface PaymentAccountService {

    /**
     * Save a paymentAccount.
     *
     * @param paymentAccountDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentAccountDTO save(PaymentAccountDTO paymentAccountDTO);

    /**
     * Get all the paymentAccounts.
     *
     * @return the list of entities.
     */
    List<PaymentAccountDTO> findAll();


    /**
     * Get all the paymentAccounts by current user.
     *
     * @return the list of entities.
     */
    List<PaymentAccountDTO> findByOwnerIsCurrentUser();



    /**
     * Get the "id" paymentAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentAccountDTO> findOne(Long id);

    /**
     * Delete the "id" paymentAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
