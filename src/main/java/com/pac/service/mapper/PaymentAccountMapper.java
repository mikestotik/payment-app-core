package com.pac.service.mapper;


import com.pac.domain.*;
import com.pac.service.dto.PaymentAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentAccount} and its DTO {@link PaymentAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PaymentAccountMapper extends EntityMapper<PaymentAccountDTO, PaymentAccount> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    PaymentAccountDTO toDto(PaymentAccount paymentAccount);

    @Mapping(source = "ownerId", target = "owner")
    PaymentAccount toEntity(PaymentAccountDTO paymentAccountDTO);

    default PaymentAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentAccount paymentAccount = new PaymentAccount();
        paymentAccount.setId(id);
        return paymentAccount;
    }
}
