package com.fintech.api.entity;

import com.fintech.api.type.Currency;
import com.fintech.api.type.OfferStatus;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

import static com.fintech.api.type.OfferStatus.ACTIVE;

@Data
@Entity
@Table(name = "offers")
public class OfferEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @Column(name = "expiry_date")
    private OffsetDateTime expiryDate;

    public boolean isExpired() {
        return this.expiryDate.isBefore(OffsetDateTime.now());
    }
}
