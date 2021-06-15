package com.facens.pooii.ac1.ac1.repositories;


import com.facens.pooii.ac1.ac1.entities.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

}
