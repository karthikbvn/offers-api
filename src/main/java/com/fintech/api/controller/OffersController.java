package com.fintech.api.controller;

import com.fintech.api.model.Offer;
import com.fintech.api.service.OfferService;
import com.fintech.api.validation.CreateChecks;
import com.fintech.api.validation.ValidationUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(path = "/offers", produces = "application/json")
@Api(tags = "product-offers")
public class OffersController {

    @Autowired
    private OfferService offerService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer){
        ValidationUtils.validate(offer, CreateChecks.class);
        Offer createdOffer = offerService.createOffer(offer);
        return new ResponseEntity<>(createdOffer, CREATED);
    }

    @GetMapping(path = "/{id}")
    public Offer getOfferById(@PathVariable("id") String offerId){
        return offerService.getOffer(offerId);
    }

    @GetMapping
    public List<Offer> getOffers(){
        return offerService.getOffers();
    }

    @PostMapping(path = "/{id}/cancel")
    public Offer cancelOffer(@PathVariable("id") String offerId){
        return offerService.cancelOffer(offerId);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteOffer(@PathVariable("id") String offerId){
        offerService.deleteOffer(offerId);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
