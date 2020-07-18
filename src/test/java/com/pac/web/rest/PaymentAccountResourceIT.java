package com.pac.web.rest;

import com.pac.PaymentAppCore;
import com.pac.domain.PaymentAccount;
import com.pac.repository.PaymentAccountRepository;
import com.pac.service.PaymentAccountService;
import com.pac.service.dto.PaymentAccountDTO;
import com.pac.service.mapper.PaymentAccountMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaymentAccountResource} REST controller.
 */
@SpringBootTest(classes = PaymentAppCore.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentAccountResourceIT {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BSB = "AAAAAAAAAA";
    private static final String UPDATED_BSB = "BBBBBBBBBB";

    @Autowired
    private PaymentAccountRepository paymentAccountRepository;

    @Autowired
    private PaymentAccountMapper paymentAccountMapper;

    @Autowired
    private PaymentAccountService paymentAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentAccountMockMvc;

    private PaymentAccount paymentAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentAccount createEntity(EntityManager em) {
        PaymentAccount paymentAccount = new PaymentAccount()
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .bsb(DEFAULT_BSB);
        return paymentAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentAccount createUpdatedEntity(EntityManager em) {
        PaymentAccount paymentAccount = new PaymentAccount()
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bsb(UPDATED_BSB);
        return paymentAccount;
    }

    @BeforeEach
    public void initTest() {
        paymentAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentAccount() throws Exception {
        int databaseSizeBeforeCreate = paymentAccountRepository.findAll().size();
        // Create the PaymentAccount
        PaymentAccountDTO paymentAccountDTO = paymentAccountMapper.toDto(paymentAccount);
        restPaymentAccountMockMvc.perform(post("/api/payment-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentAccount in the database
        List<PaymentAccount> paymentAccountList = paymentAccountRepository.findAll();
        assertThat(paymentAccountList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentAccount testPaymentAccount = paymentAccountList.get(paymentAccountList.size() - 1);
        assertThat(testPaymentAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testPaymentAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testPaymentAccount.getBsb()).isEqualTo(DEFAULT_BSB);
    }

    @Test
    @Transactional
    public void createPaymentAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentAccountRepository.findAll().size();

        // Create the PaymentAccount with an existing ID
        paymentAccount.setId(1L);
        PaymentAccountDTO paymentAccountDTO = paymentAccountMapper.toDto(paymentAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentAccountMockMvc.perform(post("/api/payment-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentAccount in the database
        List<PaymentAccount> paymentAccountList = paymentAccountRepository.findAll();
        assertThat(paymentAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaymentAccounts() throws Exception {
        // Initialize the database
        paymentAccountRepository.saveAndFlush(paymentAccount);

        // Get all the paymentAccountList
        restPaymentAccountMockMvc.perform(get("/api/payment-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bsb").value(hasItem(DEFAULT_BSB)));
    }

    @Test
    @Transactional
    public void getPaymentAccount() throws Exception {
        // Initialize the database
        paymentAccountRepository.saveAndFlush(paymentAccount);

        // Get the paymentAccount
        restPaymentAccountMockMvc.perform(get("/api/payment-accounts/{id}", paymentAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.bsb").value(DEFAULT_BSB));
    }
    @Test
    @Transactional
    public void getNonExistingPaymentAccount() throws Exception {
        // Get the paymentAccount
        restPaymentAccountMockMvc.perform(get("/api/payment-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentAccount() throws Exception {
        // Initialize the database
        paymentAccountRepository.saveAndFlush(paymentAccount);

        int databaseSizeBeforeUpdate = paymentAccountRepository.findAll().size();

        // Update the paymentAccount
        PaymentAccount updatedPaymentAccount = paymentAccountRepository.findById(paymentAccount.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentAccount are not directly saved in db
        em.detach(updatedPaymentAccount);
        updatedPaymentAccount
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bsb(UPDATED_BSB);
        PaymentAccountDTO paymentAccountDTO = paymentAccountMapper.toDto(updatedPaymentAccount);

        restPaymentAccountMockMvc.perform(put("/api/payment-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentAccountDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentAccount in the database
        List<PaymentAccount> paymentAccountList = paymentAccountRepository.findAll();
        assertThat(paymentAccountList).hasSize(databaseSizeBeforeUpdate);
        PaymentAccount testPaymentAccount = paymentAccountList.get(paymentAccountList.size() - 1);
        assertThat(testPaymentAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testPaymentAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testPaymentAccount.getBsb()).isEqualTo(UPDATED_BSB);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentAccount() throws Exception {
        int databaseSizeBeforeUpdate = paymentAccountRepository.findAll().size();

        // Create the PaymentAccount
        PaymentAccountDTO paymentAccountDTO = paymentAccountMapper.toDto(paymentAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentAccountMockMvc.perform(put("/api/payment-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentAccount in the database
        List<PaymentAccount> paymentAccountList = paymentAccountRepository.findAll();
        assertThat(paymentAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentAccount() throws Exception {
        // Initialize the database
        paymentAccountRepository.saveAndFlush(paymentAccount);

        int databaseSizeBeforeDelete = paymentAccountRepository.findAll().size();

        // Delete the paymentAccount
        restPaymentAccountMockMvc.perform(delete("/api/payment-accounts/{id}", paymentAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentAccount> paymentAccountList = paymentAccountRepository.findAll();
        assertThat(paymentAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
