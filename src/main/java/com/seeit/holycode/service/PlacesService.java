package com.seeit.holycode.service;

import com.seeit.holycode.exception.PlacesException;
import com.seeit.holycode.model.locations.PlaceLocalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static java.lang.String.format;

@Service
@Slf4j
public class PlacesService {

    private WebClient webClient = WebClient.create();

    @Value("${web.client.places.base.url}")
    private String baseUrl;

    public PlaceLocalEntry places(final String id) {
        try {
            return webClient
                    .get()
                    .uri(format("%s/%s", baseUrl, id))
                    .retrieve()
                    .bodyToMono(PlaceLocalEntry.class)
//                    .log()
//                    .cache(Duration.of(10, ChronoUnit.SECONDS))
                    .block();

        } catch (WebClientResponseException e) {
            log.error(
                    format("Error in places(): code %s | body %s", e.getRawStatusCode(), e.getResponseBodyAsString()),
                    e);

            throw new PlacesException(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {
            log.error("Error in places()", e);
            throw new PlacesException(e.getMessage(), 500);
        }
    }
}
