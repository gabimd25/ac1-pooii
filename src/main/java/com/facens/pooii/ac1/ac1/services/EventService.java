package com.facens.pooii.ac1.ac1.services;

import java.util.ArrayList;
import java.util.List;

import com.facens.pooii.ac1.ac1.dto.EventDTO;
import com.facens.pooii.ac1.ac1.entities.Event;
import com.facens.pooii.ac1.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository repo;

    public List<EventDTO> getEvents(){
        List<Event> list = repo.findAll();
        List<EventDTO> listDTO = new ArrayList<>();

        for(Event e : list){
            EventDTO dto = new EventDTO(e.getId(), e.getName(),e.getDescription(), e.getPlace(),
                e.getStartDate(), e.getEndDate(), e.getStartTime(), e.getEndTime());
            listDTO.add(dto);
        }
        return listDTO;
    }
}
