package com.fintech.api.model;

import com.fintech.api.type.Currency;
import com.fintech.api.type.OfferStatus;
import com.fintech.api.validation.CreateChecks;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

@Data
public class Offer {

    @NotBlank(message = "error.client.id.blank", groups = CreateChecks.class)
    private String id;

    @NotBlank(message = "error.client.description.blank", groups = CreateChecks.class)
    private String description;

    @NotNull(message = "error.client.price.blank", groups = CreateChecks.class)
    private Double price;

    @NotNull(message = "error.client.currency.blank", groups = CreateChecks.class)
    private Currency currency;

    @NotBlank(message = "error.client.product-id.blank", groups = CreateChecks.class)
    private String productId;

    @Null(message = "error.client.status.not-blank", groups = CreateChecks.class)
    private OfferStatus status;

    @NotNull(message = "error.client.expiry-date.blank", groups = CreateChecks.class)
    private OffsetDateTime expiryDate;
}
