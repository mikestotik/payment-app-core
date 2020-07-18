package com.pac.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentAccountMapperTest {

    private PaymentAccountMapper paymentAccountMapper;

    @BeforeEach
    public void setUp() {
        paymentAccountMapper = new PaymentAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paymentAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentAccountMapper.fromId(null)).isNull();
    }
}
