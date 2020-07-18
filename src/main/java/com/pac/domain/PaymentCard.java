package com.pac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A PaymentCard.
 */
@Entity
@Table(name = "pa_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "card_number")
    private Long cardNumber;

    @Column(name = "expiry_month", length = 2)
    private Integer expiryMonth;

    @Column(name = "expiry_year", length = 2)
    private Integer expiryYear;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentCards", allowSetters = true)
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public PaymentCard fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public PaymentCard cardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public PaymentCard expiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public PaymentCard expiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public User getOwner() {
        return owner;
    }

    public PaymentCard owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentCard)) {
            return false;
        }
        return id != null && id.equals(((PaymentCard) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentCard{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", cardNumber=" + getCardNumber() +
            ", expiryMonth=" + getExpiryMonth() +
            ", expiryYear=" + getExpiryYear() +
            "}";
    }
}
