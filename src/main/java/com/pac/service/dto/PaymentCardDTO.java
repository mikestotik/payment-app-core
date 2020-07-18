package com.pac.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pac.domain.PaymentCard} entity.
 */
public class PaymentCardDTO implements Serializable {
    
    private Long id;

    private String fullName;

    private Long cardNumber;

    private Integer expiryMonth;

    private Integer expiryYear;


    private Long ownerId;

    private String ownerLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
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
        if (!(o instanceof PaymentCardDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentCardDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentCardDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", cardNumber=" + getCardNumber() +
            ", expiryMonth=" + getExpiryMonth() +
            ", expiryYear=" + getExpiryYear() +
            ", ownerId=" + getOwnerId() +
            ", ownerLogin='" + getOwnerLogin() + "'" +
            "}";
    }
}
