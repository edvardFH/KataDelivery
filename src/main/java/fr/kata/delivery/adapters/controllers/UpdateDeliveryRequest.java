package fr.kata.delivery.adapters.controllers;

import jakarta.validation.constraints.NotNull;

public record UpdateDeliveryRequest(
        @NotNull AddressDTO address,
        @NotNull DeliverySlotDTO slot
) {}
