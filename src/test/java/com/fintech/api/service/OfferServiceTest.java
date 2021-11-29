package com.fintech.api.service;

import com.fintech.api.entity.OfferEntity;
import com.fintech.api.model.Offer;
import com.fintech.api.repository.OffersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Optional;

import static com.fintech.api.type.Currency.GBP;
import static com.fintech.api.type.OfferStatus.ACTIVE;
import static com.fintech.api.type.OfferStatus.CANCELLED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {

    @InjectMocks private OfferService offerService;
    @Mock private OffersRepository offersRepository;

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowExceptionCreateOfferWithExpiredDate(){
        // GIVEN
        var offer = buildOfferRequest();
        offer.setExpiryDate(OffsetDateTime.parse("2021-02-15T21:48:34.000Z"));

        // THEN
        offerService.createOffer(offer);
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowExceptionCreateOfferWithDuplicateOffer(){
        // GIVEN
        var offer = buildOfferRequest();

        // WHEN
        when(offersRepository.existsById(eq(offer.getId()))).thenReturn(true);

        // THEN
        offerService.createOffer(offer);
    }

    @Test
    public void shouldCreateNewOffer(){
        // GIVEN
        var offer = buildOfferRequest();
        var offerEntity = buildOfferResponse();

        // WHEN
        when(offersRepository.existsById(eq(offer.getId()))).thenReturn(false);
        when((offersRepository.save(eq(offerEntity)))).thenReturn(offerEntity);

        // THEN
        var response = offerService.createOffer(offer);
        assertEquals(ACTIVE, response.getStatus());
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowExceptionCancelOfferForInvalidOffer(){
        // GIVEN
        var offerToCancel = "OFFER_50";

        // WHEN
        when(offersRepository.findById(eq(offerToCancel))).thenReturn(Optional.empty());

        // THEN
        offerService.cancelOffer(offerToCancel);
    }

    @Test
    public void shouldCancelOffer(){
        // GIVEN
        var offerToCancel = "OFFER_50";
        var offerEntity = buildOfferResponse();
        var cancelledOffer = buildOfferResponse();
        cancelledOffer.setStatus(CANCELLED);

        // WHEN
        when(offersRepository.findById(eq(offerToCancel))).thenReturn(Optional.of(offerEntity));
        when((offersRepository.save(eq(offerEntity)))).thenReturn(cancelledOffer);

        // THEN
        var response = offerService.cancelOffer(offerToCancel);
        assertEquals(CANCELLED, response.getStatus());
    }

    @Test(expected = ResponseStatusException.class)
    public void shouldThrowExceptionGetOfferForInvalidOffer(){
        // GIVEN
        var offerToFetch = "OFFER324234c2";
        var offerEntity = buildOfferResponse();

        // WHEN
        when(offersRepository.findById(eq(offerToFetch))).thenReturn(Optional.empty());

        // THEN
        offerService.getOffer(offerToFetch);
    }

    @Test
    public void shouldGetOfferById(){
        // GIVEN
        var offerToFetch = "OFFER_50";
        var offerEntity = buildOfferResponse();

        // WHEN
        when(offersRepository.findById(eq(offerToFetch))).thenReturn(Optional.of(offerEntity));

        // THEN
        var response = offerService.getOffer(offerToFetch);
        assertNotNull(response);
    }

    private Offer buildOfferRequest() {
        var offer = new Offer();
        offer.setId("OFFER_50");
        offer.setDescription("50% offer on the selected product");
        offer.setCurrency(GBP);
        offer.setPrice(25.00);
        offer.setExpiryDate(OffsetDateTime.parse("2022-02-15T21:48:34.000Z"));
        return offer;
    }

    private OfferEntity buildOfferResponse() {
        var offer = new OfferEntity();
        offer.setId("OFFER_50");
        offer.setDescription("50% offer on the selected product");
        offer.setCurrency(GBP);
        offer.setStatus(ACTIVE);
        offer.setPrice(25.00);
        offer.setExpiryDate(OffsetDateTime.parse("2022-02-15T21:48:34.000Z"));
        return offer;
    }
}
