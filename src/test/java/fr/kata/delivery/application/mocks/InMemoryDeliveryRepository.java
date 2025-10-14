package fr.kata.delivery.application.mocks;

import fr.kata.delivery.application.DeliveryRepository;
import fr.kata.delivery.domain.Delivery;
import fr.kata.delivery.domain.DeliveryId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDeliveryRepository implements DeliveryRepository {
    private final Map<String, Delivery> store = new ConcurrentHashMap<>();


    @Override
    public Optional<Delivery> findById(DeliveryId id) {
        return Optional.ofNullable(store.get(id.value()));
    }


    @Override
    public Delivery save(Delivery delivery) {
        store.put(delivery.id().value(), delivery);
        return delivery;
    }


    public void put(Delivery delivery) { save(delivery); }
}
