package com.pac.service;

import com.pac.service.dto.PaymentCardDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pac.domain.PaymentCard}.
 */
public interface PaymentCardService {

    /**
     * Save a paymentCard.
     *
     * @param paymentCardDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentCardDTO save(PaymentCardDTO paymentCardDTO);

    /**
     * Get all the paymentCards.
     *
     * @return the list of entities.
     */
    List<PaymentCardDTO> findAll();

    /**
     * Get all the paymentCards by current user.
     *
     * @return the list of entities.
     */
    List<PaymentCardDTO> findByOwnerIsCurrentUser();


    /**
     * Get the "id" paymentCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentCardDTO> findOne(Long id);

    /**
     * Delete the "id" paymentCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
