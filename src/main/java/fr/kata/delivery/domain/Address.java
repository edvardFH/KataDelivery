package fr.kata.delivery.domain;

public record Address(String line1, String line2, String postalCode, String city, String countryCode) {
    public Address {
        if (line1 == null || line1.isBlank()) throw new DomainException("Address.line1 must be provided");
        if (line2 == null) throw new DomainException("Address.line2 can be blank but not null");
        if (postalCode == null || postalCode.isBlank()) throw new DomainException("Address.postalCode must be provided");
        if (city == null || city.isBlank()) throw new DomainException("Address.city must be provided");
        if (countryCode == null || countryCode.isBlank()) throw new DomainException("Address.countryCode must be provided");
        countryCode = countryCode.trim().toUpperCase();
    }
}
