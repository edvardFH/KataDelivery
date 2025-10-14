package fr.kata.delivery.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeliverySlotTest {

    private DeliverySlot slot;

    private static LocalDateTime t(int h, int m) {
        return LocalDateTime.of(2025, 1, 1, h, m);
    }

    @BeforeEach
    void setUp() {
        slot = new DeliverySlot(t(10, 0), t(12, 0));
    }

    @Test
    void endMustBeAfterStartOtherwiseThrows() {
        LocalDateTime start = t(10, 0);
        LocalDateTime end   = t(9, 59);
        assertThrows(DomainException.class, () -> new DeliverySlot(start, end));
    }

    @Test
    void containsReturnsTrueForInteriorInstant() {
        assertTrue(slot.contains(t(11, 0)));
    }

    @Test
    void containsIsInclusiveAtStart() {
        assertTrue(slot.contains(t(10, 0)), "start boundary should be included");
    }

    @Test
    void containsIsExclusiveAtEnd() {
        assertFalse(slot.contains(t(12, 0)), "end boundary should be excluded");
    }

    @Test
    void containsReturnsFalseBeforeStart() {
        assertFalse(slot.contains(t(9, 59)));
    }

    @Test
    void containsReturnsFalseAfterEnd() {
        assertFalse(slot.contains(t(12, 1)));
    }
}

