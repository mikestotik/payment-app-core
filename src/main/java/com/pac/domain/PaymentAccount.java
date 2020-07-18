package com.pac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PaymentAccount.
 */
@Entity
@Table(name = "pa_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bsb")
    private String bsb;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentAccounts", allowSetters = true)
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public PaymentAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public PaymentAccount accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBsb() {
        return bsb;
    }

    public PaymentAccount bsb(String bsb) {
        this.bsb = bsb;
        return this;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

    public User getOwner() {
        return owner;
    }

    public PaymentAccount owner(User user) {
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
        if (!(o instanceof PaymentAccount)) {
            return false;
        }
        return id != null && id.equals(((PaymentAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentAccount{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", bsb='" + getBsb() + "'" +
            "}";
    }
}
