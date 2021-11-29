package com.fintech.api.service;

import com.fintech.api.entity.OfferEntity;
import com.fintech.api.model.Offer;
import com.fintech.api.repository.OffersRepository;
import com.fintech.api.type.OfferStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

import static com.fintech.api.type.OfferStatus.ACTIVE;
import static com.fintech.api.type.OfferStatus.EXPIRED;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class OfferService {

    @Autowired
    private OffersRepository offersRepository;

    private final ModelMapper mapper = new ModelMapper();

    public Offer getOffer(String offerId){
        var offerEntity = offersRepository.findById(offerId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Offer not found"));
        if(offerEntity.isExpired() && offerEntity.getStatus().equals(OfferStatus.ACTIVE)){
            updateStatusAsExpired(offerEntity);
        }
        return mapper.map(offerEntity, Offer.class);
    }

    private void updateStatusAsExpired(OfferEntity offerEntity) {
        offerEntity.setStatus(EXPIRED);
        offersRepository.save(offerEntity);
    }

    /**
     * Updates the offer status as CANCELLED in database
     * @param offerId
     * @return
     */
    public Offer cancelOffer(String offerId) {
        var offerEntity = offersRepository.findById(offerId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Offer not found"));
        offerEntity.setStatus(OfferStatus.CANCELLED);
        var cancelledOffer = offersRepository.save(offerEntity);
        return mapper.map(cancelledOffer, Offer.class);
    }

    /**
     * Lists all offers
     * @return
     */
    public List<Offer> getOffers(){
        return offersRepository.findAll()
                .stream()
                .map(offerEntity -> mapper.map(offerEntity, Offer.class))
                .collect(toList());
    }

    /**
     * Creates an offer with the given expiry date in database
     *
     * @param offer
     * @return
     */
    public Offer createOffer(Offer offer){
        var createdEntity = mapper.map(offer, OfferEntity.class);
        validateOffer(createdEntity);
        createdEntity.setStatus(ACTIVE);
        offersRepository.save(createdEntity);
        return mapper.map(createdEntity, Offer.class);
    }

    private void validateOffer(OfferEntity offer) {
        if(offer.isExpired()){
            throw new ResponseStatusException(BAD_REQUEST, "Offer expiryDate is before now!");
        }
        if(offersRepository.existsById(offer.getId())){
            throw new ResponseStatusException(BAD_REQUEST, "Offer already exists!");
        }
    }

    public void deleteOffer(String offerId) {
        var offerEntity = offersRepository.findById(offerId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Offer not found"));
        offersRepository.delete(offerEntity);
    }

    /**
     * Updates offers as expired if the expiry date is before now - runs in a scheduler
     */
    public void expireOffers(){
        var expiredOffers = offersRepository.findByExpiryDateBefore(OffsetDateTime.now());
        expiredOffers.forEach(offer -> offer.setStatus(EXPIRED));
        offersRepository.saveAll(unmodifiableList(expiredOffers));
    }

}
