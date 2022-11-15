package com.seeit.holycode.controller;

import com.seeit.holycode.exception.PlacesException;
import com.seeit.holycode.model.locations.PlaceLocalEntry;
import com.seeit.holycode.service.PlacesService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/places")
public class PlacesController {

    @Autowired
    private PlacesService placesService;

    @Operation(
            summary = "Retrieves a location entry based on its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success retrieve entry"),
                    @ApiResponse(responseCode = "401", description = "Request not authorized", content = {@Content(schema = @Schema(type = "ErrorResponse"))}),
                    @ApiResponse(responseCode = "403", description = "Request forbidden"),
                    @ApiResponse(responseCode = "404", description = "Resource not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")})

    @GetMapping("/{id}")
    @Bulkhead(name = "bulkhead", fallbackMethod = "bulkheadFallBack")
    @RateLimiter(name = "rateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "circuitBreakerFallback")
    @Retry(name = "retry", fallbackMethod = "retryFallBack")
    public ResponseEntity<PlaceLocalEntry> places(@PathVariable("id") final String id) {
        return ResponseEntity.ok(placesService.places(id));
    }

    public ResponseEntity<PlaceLocalEntry> bulkheadFallBack(RequestNotPermitted exception) {
        log.info("Bulkhead has applied. No further calls are getting accepted");

        throw new PlacesException(
                "Bulkead limit exceeded",
                HttpStatus.TOO_MANY_REQUESTS.value());
    }

    public ResponseEntity<PlaceLocalEntry> rateLimitFallBack(RequestNotPermitted exception) {
        log.info("Rate limit has applied. No further calls are getting accepted");
        throw new PlacesException(
                "Rate limit exceeded",
                HttpStatus.TOO_MANY_REQUESTS.value());
    }

    public ResponseEntity<PlaceLocalEntry> circuitBreakerFallback(Exception e) {
        log.info("Circuit breaker open. No further calls are getting accepted");
        throw new PlacesException(
                "Circuit breaker open",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public ResponseEntity<PlaceLocalEntry> retryFallBack(Exception e) {
        log.info("Retry fallback status: failed");
        throw new PlacesException(
                "Retry failed",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
