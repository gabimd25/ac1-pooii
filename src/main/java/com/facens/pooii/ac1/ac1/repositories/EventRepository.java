package com.facens.pooii.ac1.ac1.repositories;

import com.facens.pooii.ac1.ac1.entities.Event;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

    @Query("SELECT e FROM Event e")
    public Page<Event> find(PageRequest pageRequest);
}
