package fr.kata.delivery.adapters.persistence.mappers;

import fr.kata.delivery.adapters.persistence.entities.AddressEmbeddable;
import fr.kata.delivery.adapters.persistence.entities.DeliveryEntity;
import fr.kata.delivery.adapters.persistence.entities.DeliverySlotEmbeddable;
import fr.kata.delivery.domain.Address;
import fr.kata.delivery.domain.CustomerId;
import fr.kata.delivery.domain.Delivery;
import fr.kata.delivery.domain.DeliveryId;
import fr.kata.delivery.domain.DeliverySlot;

public final class DeliveryJpaMapper {
    private DeliveryJpaMapper() {}


    public static DeliveryEntity toEntity(Delivery d) {
        Address a = d.address();
        AddressEmbeddable addr = new AddressEmbeddable(a.line1(), a.line2(), a.postalCode(), a.city(), a.countryCode());
        DeliverySlot s = d.slot();
        DeliverySlotEmbeddable slot = new DeliverySlotEmbeddable(s.start(), s.end());
        return new DeliveryEntity(
                d.id().value(),
                d.customerId().value(),
                addr,
                slot,
                d.state(),
                d.version()
        );
    }


    public static Delivery toDomain(DeliveryEntity e) {
        Address addr = new Address(e.getAddress().getLine1(), e.getAddress().getLine2(), e.getAddress().getPostalCode(), e.getAddress().getCity(), e.getAddress().getCountryCode());
        DeliverySlot slot = new DeliverySlot(e.getSlot().getStart(), e.getSlot().getEnd());
        return new Delivery(
                new DeliveryId(e.getId()),
                new CustomerId(e.getCustomerId()),
                addr,
                slot,
                e.getState(),
                e.getVersion()
        );
    }
}
