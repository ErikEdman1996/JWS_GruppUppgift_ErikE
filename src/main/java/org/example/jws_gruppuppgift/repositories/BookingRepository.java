package org.example.jws_gruppuppgift.repositories;

import org.example.jws_gruppuppgift.entities.TravelBooking;
import org.example.jws_gruppuppgift.entities.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<TravelBooking, Long>
{
    List<TravelBooking> findByCustomer(String customer);
    List<TravelBooking> findByCustomerAndStatusIn(String customer, List<TravelBooking.BookingStatus> statuses);
    List<TravelBooking> findByStatusIn(List<TravelBooking.BookingStatus> statuses);
    List<TravelBooking> findByTravel(Travel travel);
}
