package com.pac.service.impl;

import com.pac.service.PaymentAccountService;
import com.pac.domain.PaymentAccount;
import com.pac.repository.PaymentAccountRepository;
import com.pac.service.UserService;
import com.pac.service.dto.PaymentAccountDTO;
import com.pac.service.mapper.PaymentAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PaymentAccount}.
 */
@Service
@Transactional
public class PaymentAccountServiceImpl implements PaymentAccountService {

    private final Logger log = LoggerFactory.getLogger(PaymentAccountServiceImpl.class);

    private final PaymentAccountRepository paymentAccountRepository;

    private final PaymentAccountMapper paymentAccountMapper;
    private UserService userService;

    public PaymentAccountServiceImpl(
        PaymentAccountRepository paymentAccountRepository,
        PaymentAccountMapper paymentAccountMapper,
        UserService userService) {
        this.paymentAccountRepository = paymentAccountRepository;
        this.paymentAccountMapper = paymentAccountMapper;
        this.userService = userService;
    }

    @Override
    public PaymentAccountDTO save(PaymentAccountDTO paymentAccountDTO) {
        log.debug("Request to save PaymentAccount : {}", paymentAccountDTO);
        PaymentAccount paymentAccount = paymentAccountMapper.toEntity(paymentAccountDTO);

        if (paymentAccount.getId() == null) {
            userService.getUserWithAuthorities()
                .ifPresent(paymentAccount::setOwner);
        }

        paymentAccount = paymentAccountRepository.save(paymentAccount);
        return paymentAccountMapper.toDto(paymentAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentAccountDTO> findAll() {
        log.debug("Request to get all PaymentAccounts");
        return paymentAccountRepository.findAll().stream()
            .map(paymentAccountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentAccountDTO> findByOwnerIsCurrentUser() {
        log.debug("Request to get all PaymentAccounts");
        return paymentAccountRepository.findByOwnerIsCurrentUser()
            .stream()
            .map(paymentAccountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentAccountDTO> findOne(Long id) {
        log.debug("Request to get PaymentAccount : {}", id);
        return paymentAccountRepository.findById(id)
            .map(paymentAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentAccount : {}", id);
        paymentAccountRepository.deleteById(id);
    }
}
