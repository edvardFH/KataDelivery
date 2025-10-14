package fr.kata.delivery.adapters.controllers;

import fr.kata.delivery.application.CustomerDeliveryService;
import fr.kata.delivery.domain.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/deliveries")
@Validated
public class DeliveryController {

    private static final String CUSTOMER_ID_HEADER = "X-Customer-Id";

    private final CustomerDeliveryService service;

    public DeliveryController(CustomerDeliveryService service) {
        this.service = service;
    }

    @GetMapping("/{deliveryId}")
    public DeliveryResponse getDelivery(
            @PathVariable String deliveryId,
            @RequestHeader(name = CUSTOMER_ID_HEADER, required = false) String customerHeader) {
        Delivery d = service.viewDelivery(new DeliveryId(deliveryId), customerFromHeader(customerHeader));
        return DeliveryResponse.from(d);
    }

    @PatchMapping("/{deliveryId}")
    public DeliveryResponse updateDelivery(
            @PathVariable String deliveryId,
            @RequestHeader(name = CUSTOMER_ID_HEADER, required = false) String customerHeader,
            @Valid @RequestBody UpdateDeliveryRequest body) {
        Address address = body.address().toDomain();
        DeliverySlot slot = body.slot().toDomain();
        Delivery updated = service.updateDeliveryDetails(new DeliveryId(deliveryId), customerFromHeader(customerHeader), address, slot);
        return DeliveryResponse.from(updated);
    }

    private static CustomerId customerFromHeader(String customerHeader) {
        if (customerHeader == null || customerHeader.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing " + CUSTOMER_ID_HEADER + " header");
        }
        return new CustomerId(customerHeader);
    }
}
