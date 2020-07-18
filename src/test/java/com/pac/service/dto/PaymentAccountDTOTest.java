package com.pac.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pac.web.rest.TestUtil;

public class PaymentAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentAccountDTO.class);
        PaymentAccountDTO paymentAccountDTO1 = new PaymentAccountDTO();
        paymentAccountDTO1.setId(1L);
        PaymentAccountDTO paymentAccountDTO2 = new PaymentAccountDTO();
        assertThat(paymentAccountDTO1).isNotEqualTo(paymentAccountDTO2);
        paymentAccountDTO2.setId(paymentAccountDTO1.getId());
        assertThat(paymentAccountDTO1).isEqualTo(paymentAccountDTO2);
        paymentAccountDTO2.setId(2L);
        assertThat(paymentAccountDTO1).isNotEqualTo(paymentAccountDTO2);
        paymentAccountDTO1.setId(null);
        assertThat(paymentAccountDTO1).isNotEqualTo(paymentAccountDTO2);
    }
}
