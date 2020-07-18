package com.pac.service.dto;

import java.time.Instant;
import java.io.Serializable;
import com.pac.domain.enumeration.MethodType;

/**
 * A DTO for the {@link com.pac.domain.Payment} entity.
 */
public class PaymentDTO implements Serializable {
    
    private Long id;

    private Double amount;

    private MethodType methodType;

    private Long methodId;

    private Instant created;


    private Long ownerId;

    private String ownerLogin;

    private Long contactId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public void setMethodType(MethodType methodType) {
        this.methodType = methodType;
    }

    public Long getMethodId() {
        return methodId;
    }

    public void setMethodId(Long methodId) {
        this.methodId = methodId;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
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

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", methodType='" + getMethodType() + "'" +
            ", methodId=" + getMethodId() +
            ", created='" + getCreated() + "'" +
            ", ownerId=" + getOwnerId() +
            ", ownerLogin='" + getOwnerLogin() + "'" +
            ", contactId=" + getContactId() +
            "}";
    }
}
