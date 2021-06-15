package com.facens.pooii.ac1.ac1.repositories;

import java.util.List;

import com.facens.pooii.ac1.ac1.entities.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

    /*@Query("SELECT e FROM Ticket e "+ "WHERE " + 
        "e.event.id = :id" )
    public List<Ticket> freeTickets(Long id);*/

}
