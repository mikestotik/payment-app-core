package com.pac.service.mapper;


import com.pac.domain.*;
import com.pac.service.dto.PaymentCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentCard} and its DTO {@link PaymentCardDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PaymentCardMapper extends EntityMapper<PaymentCardDTO, PaymentCard> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    PaymentCardDTO toDto(PaymentCard paymentCard);

    @Mapping(source = "ownerId", target = "owner")
    PaymentCard toEntity(PaymentCardDTO paymentCardDTO);

    default PaymentCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setId(id);
        return paymentCard;
    }
}
