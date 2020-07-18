package com.pac.service.mapper;


import com.pac.domain.*;
import com.pac.service.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ContactMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    @Mapping(source = "contact.id", target = "contactId")
    PaymentDTO toDto(Payment payment);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "contactId", target = "contact")
    Payment toEntity(PaymentDTO paymentDTO);

    default Payment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
