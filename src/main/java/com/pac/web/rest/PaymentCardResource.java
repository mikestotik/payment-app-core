package com.pac.web.rest;

import com.pac.service.PaymentCardService;
import com.pac.service.dto.PaymentCardDTO;
import com.pac.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pac.domain.PaymentCard}.
 */
@RestController
@RequestMapping("/api")
public class PaymentCardResource {

    private final Logger log = LoggerFactory.getLogger(PaymentCardResource.class);

    private static final String ENTITY_NAME = "paymentCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentCardService paymentCardService;

    public PaymentCardResource(PaymentCardService paymentCardService) {
        this.paymentCardService = paymentCardService;
    }

    /**
     * {@code POST  /payment-cards} : Create a new paymentCard.
     *
     * @param paymentCardDTO the paymentCardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentCardDTO, or with status {@code 400 (Bad Request)} if the paymentCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-cards")
    public ResponseEntity<PaymentCardDTO> createPaymentCard(@RequestBody PaymentCardDTO paymentCardDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentCard : {}", paymentCardDTO);
        if (paymentCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentCard cannot already have an ID", ENTITY_NAME, "idexists");
        }

        validate(paymentCardDTO);

        PaymentCardDTO result = paymentCardService.save(paymentCardDTO);
        return ResponseEntity.created(new URI("/api/payment-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-cards} : Updates an existing paymentCard.
     *
     * @param paymentCardDTO the paymentCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentCardDTO,
     * or with status {@code 400 (Bad Request)} if the paymentCardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-cards")
    public ResponseEntity<PaymentCardDTO> updatePaymentCard(@RequestBody PaymentCardDTO paymentCardDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentCard : {}", paymentCardDTO);
        if (paymentCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        validate(paymentCardDTO);

        PaymentCardDTO result = paymentCardService.save(paymentCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-cards} : get all the paymentCards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentCards in body.
     */
    @GetMapping("/payment-cards")
    public List<PaymentCardDTO> getAllPaymentCards() {
        log.debug("REST request to get all PaymentCards");
        return paymentCardService.findByOwnerIsCurrentUser();
    }

    /**
     * {@code GET  /payment-cards/:id} : get the "id" paymentCard.
     *
     * @param id the id of the paymentCardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentCardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-cards/{id}")
    public ResponseEntity<PaymentCardDTO> getPaymentCard(@PathVariable Long id) {
        log.debug("REST request to get PaymentCard : {}", id);
        Optional<PaymentCardDTO> paymentCardDTO = paymentCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentCardDTO);
    }

    /**
     * {@code DELETE  /payment-cards/:id} : delete the "id" paymentCard.
     *
     * @param id the id of the paymentCardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-cards/{id}")
    public ResponseEntity<Void> deletePaymentCard(@PathVariable Long id) {
        log.debug("REST request to delete PaymentCard : {}", id);
        paymentCardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    private void validate(PaymentCardDTO value) {
        if (value.getExpiryMonth() > 12) {
            throw new BadRequestAlertException("Expiry Month: The specified number must contain a value from 1 to 12 inclusive", ENTITY_NAME, "idexists");
        }

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int currentYear = localDate.getYear();

        if (value.getExpiryYear() < currentYear) {
            throw new BadRequestAlertException("Expiry Year: The specified number must contain a value not lower than the current year", ENTITY_NAME, "idexists");
        }

        if (value.getCardNumber().toString().length() != 16) {
            throw new BadRequestAlertException("Card Number: This value must be 16 characters", ENTITY_NAME, "idexists");
        }

        if (value.getFullName().split(" ").length != 2) {
            throw new BadRequestAlertException("Full Name: Cardholder field must contain two words", ENTITY_NAME, "idexists");
        }
    }
}
