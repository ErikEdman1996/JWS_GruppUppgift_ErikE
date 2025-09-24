package org.example.jws_gruppuppgift.repositories;

import org.example.jws_gruppuppgift.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long>
{

}
