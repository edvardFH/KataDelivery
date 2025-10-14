package fr.kata.delivery.domain;

public record Delivery(
        DeliveryId id,
        CustomerId customerId,
        Address address,
        DeliverySlot slot,
        DeliveryState state,
        long version
) {
    public Delivery {
        if (id == null) throw new DomainException("id must be provided");
        if (customerId == null) throw new DomainException("customerId must be provided");
        if (address == null) throw new DomainException("address must be provided");
        if (slot == null) throw new DomainException("slot must be provided");
        if (state == null) throw new DomainException("state must be provided");
        if (version < 0) throw new DomainException("version must be >= 0");
    }


    public static Delivery create(DeliveryId id, CustomerId customerId, Address address, DeliverySlot slot) {
        return new Delivery(id, customerId, address, slot, DeliveryState.ACCEPTED, 0L);
    }


    public Delivery withState(DeliveryState newState) {
        if (newState == null) throw new DomainException("newState must be provided");
        return new Delivery(id, customerId, address, slot, newState, version + 1);
    }


    public Delivery updateDetails(Address newAddress, DeliverySlot newSlot) {
        if (!state.isCustomerEditable()) throw new DomainException("Cannot modify delivery details when state is " + state);
        if (newAddress == null ) throw new DomainException("newAddress must be provided");
        if (newSlot == null ) throw new DomainException("newSlot must be provided");

        return new Delivery(id, customerId, newAddress, newSlot, state, version + 1);
    }
}
