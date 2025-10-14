package fr.kata.delivery.adapters.controllers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "UpdateDeliveryRequest")
public record UpdateDeliveryRequest(
        @NotNull AddressDTO address,
        @NotNull DeliverySlotDTO slot
) {}
