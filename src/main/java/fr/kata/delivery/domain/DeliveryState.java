package fr.kata.delivery.domain;

public enum DeliveryState {
    ACCEPTED,
    READY,
    DELAYED,
    DELIVERING,
    DELIVERED;

    public boolean isCustomerEditable() {
        return this == ACCEPTED;
    }
}
