package com.facens.pooii.ac1.ac1.repositories;

import java.time.LocalDate;

import com.facens.pooii.ac1.ac1.entities.Event;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

    @Query("SELECT e FROM Event e "+ "WHERE " + 
        "(LOWER(e.name) LIKE LOWER(CONCAT('%',:name,'%'))) AND " +
        "(e.startDate >= :startDate ) AND " +
        "(e.priceTicket <= :priceTicket ) AND " +
        "(LOWER (e.description) LIKE LOWER(CONCAT('%',:description,'%')))" )
    public Page<Event> find(Pageable pageRequest, String name, Double priceTicket,
            LocalDate startDate, String description);

}
