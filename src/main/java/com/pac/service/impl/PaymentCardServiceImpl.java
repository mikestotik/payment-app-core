package com.pac.service.impl;

import com.pac.service.PaymentCardService;
import com.pac.domain.PaymentCard;
import com.pac.repository.PaymentCardRepository;
import com.pac.service.UserService;
import com.pac.service.dto.PaymentCardDTO;
import com.pac.service.mapper.PaymentCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PaymentCard}.
 */
@Service
@Transactional
public class PaymentCardServiceImpl implements PaymentCardService {

    private final Logger log = LoggerFactory.getLogger(PaymentCardServiceImpl.class);

    private final PaymentCardRepository paymentCardRepository;

    private final PaymentCardMapper paymentCardMapper;
    private UserService userService;

    public PaymentCardServiceImpl(
        PaymentCardRepository paymentCardRepository,
        PaymentCardMapper paymentCardMapper,
        UserService userService) {
        this.paymentCardRepository = paymentCardRepository;
        this.paymentCardMapper = paymentCardMapper;
        this.userService = userService;
    }

    @Override
    public PaymentCardDTO save(PaymentCardDTO paymentCardDTO) {
        log.debug("Request to save PaymentCard : {}", paymentCardDTO);
        PaymentCard paymentCard = paymentCardMapper.toEntity(paymentCardDTO);
        paymentCard.setFullName(paymentCard.getFullName().toUpperCase());

        if (paymentCard.getId() == null) {
            userService.getUserWithAuthorities()
                .ifPresent(paymentCard::setOwner);
        }

        paymentCard = paymentCardRepository.save(paymentCard);
        return paymentCardMapper.toDto(paymentCard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentCardDTO> findAll() {
        log.debug("Request to get all PaymentCards");
        return paymentCardRepository.findAll().stream()
            .map(paymentCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentCardDTO> findByOwnerIsCurrentUser() {
        log.debug("Request to get all PaymentCards");
        return paymentCardRepository.findByOwnerIsCurrentUser().stream()
            .map(paymentCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentCardDTO> findOne(Long id) {
        log.debug("Request to get PaymentCard : {}", id);
        return paymentCardRepository.findById(id)
            .map(paymentCardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentCard : {}", id);
        paymentCardRepository.deleteById(id);
    }
}
