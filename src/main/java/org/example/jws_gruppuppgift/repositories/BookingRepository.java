package org.example.jws_gruppuppgift.repositories;

import org.example.jws_gruppuppgift.entities.Booking;
import org.example.jws_gruppuppgift.entities.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>
{
    List<Booking> findByCustomer(String customer);
    List<Booking> findByCustomerAndStatusIn(String customer, List<Booking.BookingStatus> statuses);
    List<Booking> findByStatusIn(List<Booking.BookingStatus> statuses);
    List<Booking> findByTravel(Travel travel);
}
