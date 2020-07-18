package com.pac.service.impl;

import com.pac.service.PaymentService;
import com.pac.domain.Payment;
import com.pac.repository.PaymentRepository;
import com.pac.service.UserService;
import com.pac.service.dto.PaymentDTO;
import com.pac.service.mapper.PaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Payment}.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;
    private UserService userService;

    public PaymentServiceImpl(
        PaymentRepository paymentRepository,
        PaymentMapper paymentMapper,
        UserService userService) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userService = userService;
    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {
        log.debug("Request to save Payment : {}", paymentDTO);
        Payment payment = paymentMapper.toEntity(paymentDTO);

        if (payment.getId() == null) {
            userService.getUserWithAuthorities()
                .ifPresent(payment::setOwner);
        }

        payment = paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> findAll() {
        log.debug("Request to get all Payments");
        return paymentRepository.findAll().stream()
            .map(paymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> findByOwnerIsCurrentUser() {
        log.debug("Request to get all Payments");
        return paymentRepository.findByOwnerIsCurrentUser().stream()
            .map(paymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDTO> findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findById(id)
            .map(paymentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.deleteById(id);
    }
}
