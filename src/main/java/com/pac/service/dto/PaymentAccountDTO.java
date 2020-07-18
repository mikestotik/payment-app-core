package com.pac.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pac.domain.PaymentAccount} entity.
 */
public class PaymentAccountDTO implements Serializable {
    
    private Long id;

    private String accountName;

    private String accountNumber;

    private String bsb;


    private Long ownerId;

    private String ownerLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBsb() {
        return bsb;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String userLogin) {
        this.ownerLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentAccountDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentAccountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentAccountDTO{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", bsb='" + getBsb() + "'" +
            ", ownerId=" + getOwnerId() +
            ", ownerLogin='" + getOwnerLogin() + "'" +
            "}";
    }
}
