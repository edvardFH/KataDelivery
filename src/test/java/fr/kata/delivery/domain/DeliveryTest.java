package fr.kata.delivery.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    private Address address;
    private DeliverySlot slot;
    private Delivery accepted;

    private static LocalDateTime t(int h, int m) {
        return LocalDateTime.of(2025, 1, 1, h, m);
    }

    @BeforeEach
    void setUp() {
        DeliveryId id = new DeliveryId("D-1");
        CustomerId customerId = new CustomerId("C-1");
        address = new Address("12 rue Exemple", "", "75001", "Paris", "FR");
        slot = new DeliverySlot(t(10, 30), t(12, 30));
        accepted = Delivery.create(id, customerId, address, slot);
    }

    @Test
    void createSetsAcceptedStateAndVersionZero() {
        assertEquals(DeliveryState.ACCEPTED, accepted.state());
        assertEquals(0L, accepted.version());
    }

    @Test
    void updateDetailsWhenStateIsNotAcceptedThrows() {
        Address newAddress = new Address("99 avenue Test", "Bât A", "75002", "Paris", "FR");
        DeliverySlot newSlot = new DeliverySlot(t(14, 0), t(16, 0));
        DeliveryState[] forbiddenUpdateStates = new DeliveryState[]{
                DeliveryState.READY,
                DeliveryState.DELAYED,
                DeliveryState.DELIVERING,
                DeliveryState.DELIVERED
        };

        for (DeliveryState forbidden : forbiddenUpdateStates) {
            Delivery d = accepted.withState(forbidden);
            assertThrows(DomainException.class,
                         () -> d.updateDetails(newAddress, newSlot),
                         () -> "Expected DomainException when state is " + forbidden);
        }
    }

    @Test
    void updateDetailsKeepsOtherFieldsUnchanged() {
        Address newAddress = new Address("99 avenue Test", "Bât A", "75002", "Paris", "FR");
        DeliverySlot newSlot = new DeliverySlot(t(14, 0), t(16, 0));

        Delivery updated = accepted.updateDetails(newAddress, newSlot);

        assertEquals(accepted.state(), updated.state(), "Original state must remain unchanged");
        assertEquals(address, accepted.address(), "Original address must remain unchanged");
        assertEquals(slot, accepted.slot(), "Original slot must remain unchanged");
        assertEquals(0L, accepted.version(), "Original version must remain unchanged");
    }

    @Test
    void updateDetailsModifiesAddressSlotAndIncrementsVersion() {
        Address newAddress = new Address("99 avenue Test", "Bât A", "75002", "Paris", "FR");
        DeliverySlot newSlot = new DeliverySlot(t(14, 0), t(16, 0));

        Delivery updated = accepted.updateDetails(newAddress, newSlot);

        assertEquals(newAddress, updated.address(), "Updated address must be applied");
        assertEquals(newSlot, updated.slot(), "Updated slot must be applied");
        assertEquals(accepted.version() + 1, updated.version(), "Version must increment when details change");
    }

    @Test
    void updateDetailsWithNullThrows() {
        Address someAddress = new Address("99 avenue Test", "", "75002", "Paris", "FR");
        DeliverySlot someSlot = new DeliverySlot(t(14, 0), t(16, 0));

        assertThrows(DomainException.class, () -> accepted.updateDetails(null, null), "Both null should throw");
        assertThrows(DomainException.class, () -> accepted.updateDetails(null, someSlot), "Null address should throw");
        assertThrows(DomainException.class, () -> accepted.updateDetails(someAddress, null), "Null slot should throw");
    }

    @Test
    void withStateChangesStateAndIncrementsVersion() {
        Delivery delivering = accepted.withState(DeliveryState.DELIVERING);
        assertEquals(DeliveryState.DELIVERING, delivering.state());
        assertEquals(1L, delivering.version());

        Delivery delivered = delivering.withState(DeliveryState.DELIVERED);
        assertEquals(DeliveryState.DELIVERED, delivered.state());
        assertEquals(2L, delivered.version());
    }
}

