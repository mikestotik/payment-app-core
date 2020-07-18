package com.pac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.pac.domain.enumeration.MethodType;
import org.hibernate.annotations.CreationTimestamp;

/**
 * A Payment.
 */
@Entity
@Table(name = "pa_payments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_type")
    private MethodType methodType;

    @Column(name = "method_id")
    private Long methodId;

    @CreationTimestamp
    @Column(name = "created")
    private Instant created;

    @ManyToOne
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private User owner;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private Contact contact;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Payment amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public Payment methodType(MethodType methodType) {
        this.methodType = methodType;
        return this;
    }

    public void setMethodType(MethodType methodType) {
        this.methodType = methodType;
    }

    public Long getMethodId() {
        return methodId;
    }

    public Payment methodId(Long methodId) {
        this.methodId = methodId;
        return this;
    }

    public void setMethodId(Long methodId) {
        this.methodId = methodId;
    }

    public Instant getCreated() {
        return created;
    }

    public Payment created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public User getOwner() {
        return owner;
    }

    public Payment owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Contact getContact() {
        return contact;
    }

    public Payment contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", methodType='" + getMethodType() + "'" +
            ", methodId=" + getMethodId() +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
