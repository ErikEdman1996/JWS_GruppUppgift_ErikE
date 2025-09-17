package org.example.jws_gruppuppgift.repositories;

import org.example.jws_gruppuppgift.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>
{

}
