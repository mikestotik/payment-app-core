package com.pac.web.rest;

import com.pac.PaymentAppCore;
import com.pac.domain.PaymentCard;
import com.pac.repository.PaymentCardRepository;
import com.pac.service.PaymentCardService;
import com.pac.service.dto.PaymentCardDTO;
import com.pac.service.mapper.PaymentCardMapper;

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
 * Integration tests for the {@link PaymentCardResource} REST controller.
 */
@SpringBootTest(classes = PaymentAppCore.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentCardResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CARD_NUMBER = 1L;
    private static final Long UPDATED_CARD_NUMBER = 2L;

    private static final Integer DEFAULT_EXPIRY_MONTH = 1;
    private static final Integer UPDATED_EXPIRY_MONTH = 2;

    private static final Integer DEFAULT_EXPIRY_YEAR = 1;
    private static final Integer UPDATED_EXPIRY_YEAR = 2;

    @Autowired
    private PaymentCardRepository paymentCardRepository;

    @Autowired
    private PaymentCardMapper paymentCardMapper;

    @Autowired
    private PaymentCardService paymentCardService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentCardMockMvc;

    private PaymentCard paymentCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentCard createEntity(EntityManager em) {
        PaymentCard paymentCard = new PaymentCard()
            .fullName(DEFAULT_FULL_NAME)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .expiryMonth(DEFAULT_EXPIRY_MONTH)
            .expiryYear(DEFAULT_EXPIRY_YEAR);
        return paymentCard;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentCard createUpdatedEntity(EntityManager em) {
        PaymentCard paymentCard = new PaymentCard()
            .fullName(UPDATED_FULL_NAME)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expiryMonth(UPDATED_EXPIRY_MONTH)
            .expiryYear(UPDATED_EXPIRY_YEAR);
        return paymentCard;
    }

    @BeforeEach
    public void initTest() {
        paymentCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentCard() throws Exception {
        int databaseSizeBeforeCreate = paymentCardRepository.findAll().size();
        // Create the PaymentCard
        PaymentCardDTO paymentCardDTO = paymentCardMapper.toDto(paymentCard);
        restPaymentCardMockMvc.perform(post("/api/payment-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentCardDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentCard in the database
        List<PaymentCard> paymentCardList = paymentCardRepository.findAll();
        assertThat(paymentCardList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentCard testPaymentCard = paymentCardList.get(paymentCardList.size() - 1);
        assertThat(testPaymentCard.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testPaymentCard.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testPaymentCard.getExpiryMonth()).isEqualTo(DEFAULT_EXPIRY_MONTH);
        assertThat(testPaymentCard.getExpiryYear()).isEqualTo(DEFAULT_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void createPaymentCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentCardRepository.findAll().size();

        // Create the PaymentCard with an existing ID
        paymentCard.setId(1L);
        PaymentCardDTO paymentCardDTO = paymentCardMapper.toDto(paymentCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentCardMockMvc.perform(post("/api/payment-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentCard in the database
        List<PaymentCard> paymentCardList = paymentCardRepository.findAll();
        assertThat(paymentCardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaymentCards() throws Exception {
        // Initialize the database
        paymentCardRepository.saveAndFlush(paymentCard);

        // Get all the paymentCardList
        restPaymentCardMockMvc.perform(get("/api/payment-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].expiryMonth").value(hasItem(DEFAULT_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].expiryYear").value(hasItem(DEFAULT_EXPIRY_YEAR)));
    }

    @Test
    @Transactional
    public void getPaymentCard() throws Exception {
        // Initialize the database
        paymentCardRepository.saveAndFlush(paymentCard);

        // Get the paymentCard
        restPaymentCardMockMvc.perform(get("/api/payment-cards/{id}", paymentCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentCard.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.intValue()))
            .andExpect(jsonPath("$.expiryMonth").value(DEFAULT_EXPIRY_MONTH))
            .andExpect(jsonPath("$.expiryYear").value(DEFAULT_EXPIRY_YEAR));
    }
    @Test
    @Transactional
    public void getNonExistingPaymentCard() throws Exception {
        // Get the paymentCard
        restPaymentCardMockMvc.perform(get("/api/payment-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentCard() throws Exception {
        // Initialize the database
        paymentCardRepository.saveAndFlush(paymentCard);

        int databaseSizeBeforeUpdate = paymentCardRepository.findAll().size();

        // Update the paymentCard
        PaymentCard updatedPaymentCard = paymentCardRepository.findById(paymentCard.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentCard are not directly saved in db
        em.detach(updatedPaymentCard);
        updatedPaymentCard
            .fullName(UPDATED_FULL_NAME)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expiryMonth(UPDATED_EXPIRY_MONTH)
            .expiryYear(UPDATED_EXPIRY_YEAR);
        PaymentCardDTO paymentCardDTO = paymentCardMapper.toDto(updatedPaymentCard);

        restPaymentCardMockMvc.perform(put("/api/payment-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentCardDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentCard in the database
        List<PaymentCard> paymentCardList = paymentCardRepository.findAll();
        assertThat(paymentCardList).hasSize(databaseSizeBeforeUpdate);
        PaymentCard testPaymentCard = paymentCardList.get(paymentCardList.size() - 1);
        assertThat(testPaymentCard.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testPaymentCard.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testPaymentCard.getExpiryMonth()).isEqualTo(UPDATED_EXPIRY_MONTH);
        assertThat(testPaymentCard.getExpiryYear()).isEqualTo(UPDATED_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentCard() throws Exception {
        int databaseSizeBeforeUpdate = paymentCardRepository.findAll().size();

        // Create the PaymentCard
        PaymentCardDTO paymentCardDTO = paymentCardMapper.toDto(paymentCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentCardMockMvc.perform(put("/api/payment-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentCard in the database
        List<PaymentCard> paymentCardList = paymentCardRepository.findAll();
        assertThat(paymentCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentCard() throws Exception {
        // Initialize the database
        paymentCardRepository.saveAndFlush(paymentCard);

        int databaseSizeBeforeDelete = paymentCardRepository.findAll().size();

        // Delete the paymentCard
        restPaymentCardMockMvc.perform(delete("/api/payment-cards/{id}", paymentCard.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentCard> paymentCardList = paymentCardRepository.findAll();
        assertThat(paymentCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
