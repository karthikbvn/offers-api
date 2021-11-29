package com.fintech.api.repository;

import com.fintech.api.entity.OfferEntity;
import com.fintech.api.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<OfferEntity, String> {

    public List<OfferEntity> findByExpiryDateBefore(OffsetDateTime now);
}
