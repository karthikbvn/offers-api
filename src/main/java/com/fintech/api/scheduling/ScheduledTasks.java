package com.fintech.api.scheduling;

import com.fintech.api.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.time.OffsetDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

@Component
@Slf4j
public class ScheduledTasks {

    @Autowired private OfferService offerService;

    @Scheduled(cron = "${cron.expression}")
    public void expireOffers() {
        log.info("Scheduler triggered to update offer status {}", now().format(ISO_OFFSET_DATE_TIME));
        offerService.expireOffers();
    }
}
