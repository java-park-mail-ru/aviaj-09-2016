package ru.aviaj.messagesystem;

import java.util.concurrent.atomic.AtomicInteger;

public class Address {

    private static AtomicInteger abonentIdCreator = new AtomicInteger();
    private final int abonentId;

    public Address() {
        this.abonentId = abonentIdCreator.incrementAndGet();
    }

    @Override
    public int hashCode() {
        return abonentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Address address = (Address) o;

        return abonentId == address.abonentId;

    }
}
