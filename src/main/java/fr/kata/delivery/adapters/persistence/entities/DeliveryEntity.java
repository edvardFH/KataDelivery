package fr.kata.delivery.adapters.persistence.entities;

import fr.kata.delivery.domain.DeliveryState;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "deliveries")
public class DeliveryEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 64)
    private String id;


    @Column(name = "customer_id", nullable = false, length = 64)
    private String customerId;


    @Embedded
    private AddressEmbeddable address;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "slot_start", nullable = false)),
            @AttributeOverride(name = "end", column = @Column(name = "slot_end", nullable = false))
    })
    private DeliverySlotEmbeddable slot;


    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 32)
    private DeliveryState state;


    @Version
    @Column(name = "version", nullable = false)
    private long version;

    protected DeliveryEntity() { }


    public DeliveryEntity(String id, String customerId, AddressEmbeddable address, DeliverySlotEmbeddable slot, DeliveryState state, long version) {
        this.id = id;
        this.customerId = customerId;
        this.address = address;
        this.slot = slot;
        this.state = state;
        this.version = version;
    }


    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public AddressEmbeddable getAddress() { return address; }
    public DeliverySlotEmbeddable getSlot() { return slot; }
    public DeliveryState getState() { return state; }
    public long getVersion() { return version; }


    public void setId(String id) { this.id = id; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setAddress(AddressEmbeddable address) { this.address = address; }
    public void setSlot(DeliverySlotEmbeddable slot) { this.slot = slot; }
    public void setState(DeliveryState state) { this.state = state; }
    public void setVersion(long version) { this.version = version; }
}
