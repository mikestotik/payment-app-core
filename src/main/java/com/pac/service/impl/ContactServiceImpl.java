package com.pac.service.impl;

import com.pac.service.ContactService;
import com.pac.domain.Contact;
import com.pac.repository.ContactRepository;
import com.pac.service.UserService;
import com.pac.service.dto.ContactDTO;
import com.pac.service.mapper.ContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Contact}.
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final UserService userService;

    public ContactServiceImpl(
        ContactRepository contactRepository,
        ContactMapper contactMapper,
        UserService userService) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
        this.userService = userService;
    }

    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("Request to save Contact : {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);

        if (contact.getId() == null) {
            userService.getUserWithAuthorities()
                .ifPresent(contact::setOwner);
        }

        contact = contactRepository.save(contact);
        return contactMapper.toDto(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDTO> findAll() {
        log.debug("Request to get all Contacts");
        return contactRepository.findAll().stream()
            .map(contactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDTO> findByOwnerIsCurrentUser() {
        log.debug("Request to get all Contacts");
        return contactRepository.findByOwnerIsCurrentUser().stream()
            .map(contactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ContactDTO> findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        return contactRepository.findById(id)
            .map(contactMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
    }
}
