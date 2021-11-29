package com.fintech.api.controller;

import com.fintech.api.entity.OfferEntity;
import com.fintech.api.model.Offer;
import com.fintech.api.service.OfferService;
import com.fintech.api.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

import static com.fintech.api.type.Currency.GBP;
import static com.fintech.api.type.OfferStatus.ACTIVE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OffersControllerTest {

    @InjectMocks private OffersController offersController;
    @Mock private OfferService offerService;

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionCreateOfferEmptyId(){
        // GIVEN
        var offer = buildOfferRequest();
        offer.setId("");

        // THEN
        offersController.createOffer(offer);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionCreateOfferInputStatus(){
        // GIVEN
        var offer = buildOfferRequest();
        offer.setStatus(ACTIVE);

        // THEN
        offersController.createOffer(offer);
    }

    @Test
    public void shouldCreateOffer(){
        // GIVEN
        var offer = buildOfferRequest();
        var offerResponse = buildOfferResponse();

        // WHEN
        when(offerService.createOffer(eq(offer))).thenReturn(offerResponse);

        // THEN
        var response = offersController.createOffer(offer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private Offer buildOfferRequest() {
        var offer = new Offer();
        offer.setId("OFFER_50");
        offer.setDescription("50% offer on the selected product");
        offer.setCurrency(GBP);
        offer.setProductId("TV2342");
        offer.setPrice(25.00);
        offer.setExpiryDate(OffsetDateTime.parse("2022-02-15T21:48:34.000Z"));
        return offer;
    }

    private Offer buildOfferResponse() {
        var offer = new Offer();
        offer.setId("OFFER_50");
        offer.setDescription("50% offer on the selected product");
        offer.setCurrency(GBP);
        offer.setProductId("TV2342");
        offer.setStatus(ACTIVE);
        offer.setPrice(25.00);
        offer.setExpiryDate(OffsetDateTime.parse("2022-02-15T21:48:34.000Z"));
        return offer;
    }
}
