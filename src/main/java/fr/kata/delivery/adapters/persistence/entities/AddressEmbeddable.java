package fr.kata.delivery.adapters.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AddressEmbeddable {
    @Column(name = "addr_line1", nullable = false)
    private String line1;
    @Column(name = "addr_line2")
    private String line2;
    @Column(name = "addr_postal_code", nullable = false)
    private String postalCode;
    @Column(name = "addr_city", nullable = false)
    private String city;
    @Column(name = "addr_country", nullable = false, length = 2)
    private String countryCode;


    protected AddressEmbeddable() { }


    public AddressEmbeddable(String line1, String line2, String postalCode, String city, String countryCode) {
        this.line1 = line1;
        this.line2 = line2;
        this.postalCode = postalCode;
        this.city = city;
        this.countryCode = countryCode;
    }


    public String getLine1() { return line1; }
    public String getLine2() { return line2; }
    public String getPostalCode() { return postalCode; }
    public String getCity() { return city; }
    public String getCountryCode() { return countryCode; }
}