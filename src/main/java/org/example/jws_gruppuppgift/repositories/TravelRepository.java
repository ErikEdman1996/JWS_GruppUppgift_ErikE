package org.example.jws_gruppuppgift.repositories;

import org.example.jws_gruppuppgift.entities.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long>
{

}
