package fr.kata.delivery.adapters.controllers;

import fr.kata.delivery.application.CustomerDeliveryService;
import fr.kata.delivery.domain.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "Deliveries", description = "Customer-facing delivery operations")
@RestController
@RequestMapping("/api/v1/deliveries")
@Validated
public class DeliveryController {

    private static final String CUSTOMER_ID_HEADER = "X-Customer-Id";

    private final CustomerDeliveryService service;

    public DeliveryController(CustomerDeliveryService service) {
        this.service = service;
    }

    @Operation(summary = "Get a delivery by id", description = "Returns the delivery details and current state for the authenticated customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delivery found", content = @Content(schema = @Schema(implementation = DeliveryResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid X-Customer-Id header"),
            @ApiResponse(responseCode = "403", description = "Delivery belongs to another customer"),
            @ApiResponse(responseCode = "404", description = "Delivery not found")
    })
    @GetMapping("/{deliveryId}")
    public DeliveryResponse getDelivery(
            @PathVariable String deliveryId,
            @RequestHeader(name = CUSTOMER_ID_HEADER, required = false) String customerHeader) {
        Delivery d = service.viewDelivery(new DeliveryId(deliveryId), customerFromHeader(customerHeader));
        return DeliveryResponse.from(d);
    }

    @Operation(summary = "Update address and slot", description = "Updates both the address and the time slot of an ACCEPTED delivery")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully", content = @Content(schema = @Schema(implementation = DeliveryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error on payload"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid X-Customer-Id header"),
            @ApiResponse(responseCode = "403", description = "Delivery belongs to another customer"),
            @ApiResponse(responseCode = "404", description = "Delivery not found"),
            @ApiResponse(responseCode = "409", description = "Business rule violation (e.g. not editable state or domain rule)")
    })
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
