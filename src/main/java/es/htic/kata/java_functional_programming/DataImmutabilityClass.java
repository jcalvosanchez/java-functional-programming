package es.htic.kata.java_functional_programming;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * To make the class immutable
 * - Declare class as final
 * - Make all fields private and final
 * - Offer only getter methods (not setter)
 * - Do not handle references to mutable objects (make a deep copy when creating your data, return a copy of your data)
 * - Ensure Deep Immutability
 */
public final class DataImmutabilityClass {
    private final List<String> items;
    private final List<String> immutableItems;
    private final Address address;

    public DataImmutabilityClass(List<String> items, Address address) {
        this.items = new ArrayList<>(items); // Deep copy the list
        this.immutableItems = List.copyOf(items); // Java 10+ or use Collections.unmodifiableList
        this.address = new Address(address.getStreet(), address.getCity()); // Defensive copy
    }

    public List<String> getItems() {
        return new ArrayList<>(items); // Return a copy of the list
    }
    public List<String> getImmutableItems() {
        return immutableItems; // Immutable collection, safe to return directly
    }
    public Address getAddress() {
        return new Address(address.getStreet(), address.getCity()); // Return a new copy
    }

    /**
     * Example of a record
     */
    public record Circle(double radius) {
        public Circle {
            if (radius < 0) {
                throw new IllegalArgumentException("Radius must be positive");
            }
        }
    }

    /**
     * Equivalent Address class as record and as a native class
     */
    public record AddressAsRecord(String street, String city) {}

    final class Address {
        private final String street;
        private final String city;

        private Address(String street, String city) {
            this.street = street;
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public String getStreet() {
            return street;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return Objects.equals(street, address.street) &&
                    Objects.equals(city, address.city);
        }

        @Override
        public int hashCode() {
            return Objects.hash(street, city);
        }
    }
}

