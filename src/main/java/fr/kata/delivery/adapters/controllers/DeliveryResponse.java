package fr.kata.delivery.adapters.controllers;

import fr.kata.delivery.domain.Delivery;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DeliveryResponse")
public record DeliveryResponse(
        String id,
        String customerId,
        String state,
        long version,
        AddressDTO address,
        DeliverySlotDTO slot
) {
    public static DeliveryResponse from(Delivery d) {
        return new DeliveryResponse(
                d.id().value(),
                d.customerId().value(),
                d.state().name(),
                d.version(),
                new AddressDTO(d.address().line1(), d.address().line2(), d.address().postalCode(), d.address().city(), d.address().countryCode()),
                new DeliverySlotDTO(d.slot().start(), d.slot().end())
        );
    }
}
