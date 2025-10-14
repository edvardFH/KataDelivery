package fr.kata.delivery.application;

import fr.kata.delivery.application.exceptions.BusinessRuleViolationException;
import fr.kata.delivery.application.exceptions.EntityNotFoundException;
import fr.kata.delivery.application.exceptions.OperationNotAllowedException;
import fr.kata.delivery.application.exceptions.UseCaseException;
import fr.kata.delivery.application.mocks.InMemoryDeliveryRepository;
import fr.kata.delivery.domain.Address;
import fr.kata.delivery.domain.CustomerId;
import fr.kata.delivery.domain.Delivery;
import fr.kata.delivery.domain.DeliveryId;
import fr.kata.delivery.domain.DeliverySlot;
import fr.kata.delivery.domain.DeliveryState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDeliveryServiceTest {

    private InMemoryDeliveryRepository repository;
    private CustomerDeliveryService service;

    private DeliveryId deliveryId;
    private CustomerId ownerId;
    private CustomerId otherId;
    private Address newAddress;
    private DeliverySlot newSlot;
    private DeliveryId unkownDeliveryId;


    private static LocalDateTime t(int h, int m) {
        return LocalDateTime.of(2025, 1, 1, h, m);
    }

    @BeforeEach
    void setUp() {
        repository = new InMemoryDeliveryRepository();
        service = new CustomerDeliveryService(repository);
        deliveryId = new DeliveryId("D-42");
        ownerId = new CustomerId("C-1");
        otherId = new CustomerId("C-2");
        newAddress = new Address("99 avenue Test", "Bât A", "75002", "Paris", "FR");
        newSlot = new DeliverySlot(t(14, 0), t(16, 0));
        unkownDeliveryId = new DeliveryId("D-404");

        Address address = new Address("12 rue Exemple", "", "75001", "Paris", "FR");
        DeliverySlot
                slot = new DeliverySlot(t(10, 15), t(12, 15));
        Delivery delivery = Delivery.create(deliveryId, ownerId, address, slot);

        repository.put(delivery);
    }

    @Test
    void viewDeliveryReturnsCorrectDeliveryWhenOwned() {
        Delivery delivery = service.viewDelivery(deliveryId, ownerId);

        assertEquals(deliveryId, delivery.id());
        assertEquals(ownerId, delivery.customerId());
    }

    @Test
    void viewDeliveryThrowsWhenNotOwned() {
        assertThrows(UseCaseException.class, () -> service.viewDelivery(deliveryId, otherId));
    }

    @Test
    void viewDeliveryThrowsWhenNotFound() {
        assertThrows(UseCaseException.class, () -> service.viewDelivery(unkownDeliveryId, ownerId));
    }

    @Test
    void updateDeliveryDetailsSucceedsWhenAcceptedAndOwned() {
        Delivery updated = service.updateDeliveryDetails(deliveryId, ownerId, newAddress, newSlot);

        assertEquals(newAddress, updated.address());
        assertEquals(newSlot, updated.slot());
        assertEquals(1L, updated.version());
        assertEquals(DeliveryState.ACCEPTED, updated.state());
    }

    @Test
    void updateDeliveryDetailsThrowsWhenNotOwned() {
        assertThrows(OperationNotAllowedException.class, () -> service.updateDeliveryDetails(deliveryId, otherId, newAddress, newSlot));
    }

    @Test
    void updateDeliveryDetailsThrowsWhenNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.updateDeliveryDetails(unkownDeliveryId, ownerId, newAddress, newSlot));
    }

    @Test
    void updateDeliveryDetailsThrowsWhenStateNotAccepted() {
        Delivery current = repository.findById(deliveryId).orElseThrow();
        repository.save(current.withState(DeliveryState.READY));

        assertThrows(BusinessRuleViolationException.class, () -> service.updateDeliveryDetails(deliveryId, ownerId, newAddress, newSlot));
    }

    @Test
    void updateDeliveryDetailsThrowsWhenAnyDetailIsNull() {
        assertThrows(BusinessRuleViolationException.class, () -> service.updateDeliveryDetails(deliveryId, ownerId, null, newSlot));
        assertThrows(BusinessRuleViolationException.class, () -> service.updateDeliveryDetails(deliveryId, ownerId, newAddress, null));
        assertThrows(BusinessRuleViolationException.class, () -> service.updateDeliveryDetails(deliveryId, ownerId, null, null));
    }
}
