package com.pac.web.rest;

import com.pac.service.PaymentAccountService;
import com.pac.service.dto.PaymentCardDTO;
import com.pac.web.rest.errors.BadRequestAlertException;
import com.pac.service.dto.PaymentAccountDTO;

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
 * REST controller for managing {@link com.pac.domain.PaymentAccount}.
 */
@RestController
@RequestMapping("/api")
public class PaymentAccountResource {

    private final Logger log = LoggerFactory.getLogger(PaymentAccountResource.class);

    private static final String ENTITY_NAME = "paymentAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentAccountService paymentAccountService;

    public PaymentAccountResource(PaymentAccountService paymentAccountService) {
        this.paymentAccountService = paymentAccountService;
    }

    /**
     * {@code POST  /payment-accounts} : Create a new paymentAccount.
     *
     * @param paymentAccountDTO the paymentAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentAccountDTO, or with status {@code 400 (Bad Request)} if the paymentAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-accounts")
    public ResponseEntity<PaymentAccountDTO> createPaymentAccount(@RequestBody PaymentAccountDTO paymentAccountDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentAccount : {}", paymentAccountDTO);
        if (paymentAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }

        validate(paymentAccountDTO);

        PaymentAccountDTO result = paymentAccountService.save(paymentAccountDTO);
        return ResponseEntity.created(new URI("/api/payment-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-accounts} : Updates an existing paymentAccount.
     *
     * @param paymentAccountDTO the paymentAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentAccountDTO,
     * or with status {@code 400 (Bad Request)} if the paymentAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-accounts")
    public ResponseEntity<PaymentAccountDTO> updatePaymentAccount(@RequestBody PaymentAccountDTO paymentAccountDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentAccount : {}", paymentAccountDTO);
        if (paymentAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        validate(paymentAccountDTO);

        PaymentAccountDTO result = paymentAccountService.save(paymentAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-accounts} : get all the paymentAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentAccounts in body.
     */
    @GetMapping("/payment-accounts")
    public List<PaymentAccountDTO> getAllPaymentAccounts() {
        log.debug("REST request to get all PaymentAccounts");
        return paymentAccountService.findByOwnerIsCurrentUser();
    }

    /**
     * {@code GET  /payment-accounts/:id} : get the "id" paymentAccount.
     *
     * @param id the id of the paymentAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-accounts/{id}")
    public ResponseEntity<PaymentAccountDTO> getPaymentAccount(@PathVariable Long id) {
        log.debug("REST request to get PaymentAccount : {}", id);
        Optional<PaymentAccountDTO> paymentAccountDTO = paymentAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentAccountDTO);
    }

    /**
     * {@code DELETE  /payment-accounts/:id} : delete the "id" paymentAccount.
     *
     * @param id the id of the paymentAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-accounts/{id}")
    public ResponseEntity<Void> deletePaymentAccount(@PathVariable Long id) {
        log.debug("REST request to delete PaymentAccount : {}", id);
        paymentAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    private void validate(PaymentAccountDTO value) {
        if (value.getAccountNumber().length() < 18 || value.getAccountNumber().length() > 30) {
            throw new BadRequestAlertException("Account Number: This value must be between 18 and 30 characters", ENTITY_NAME, "idexists");
        }

        if (value.getBsb().length() != 8) {
            throw new BadRequestAlertException("BSB: This value must be 8 characters", ENTITY_NAME, "idexists");
        }
    }
}
