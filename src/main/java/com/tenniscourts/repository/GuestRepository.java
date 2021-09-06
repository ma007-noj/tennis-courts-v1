package com.tenniscourts.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenniscourts.guests.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest findByName(String name);
}