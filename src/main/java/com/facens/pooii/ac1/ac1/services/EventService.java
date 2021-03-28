package com.facens.pooii.ac1.ac1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.facens.pooii.ac1.ac1.dto.EventDTO;
import com.facens.pooii.ac1.ac1.dto.EventInsertDTO;
import com.facens.pooii.ac1.ac1.entities.Event;
import com.facens.pooii.ac1.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public EventDTO getEventById(Long id){
        Optional<Event> op = repo.findById(id);
        Event ev = op.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));
        return new EventDTO(ev);
    }
    public EventDTO insert(EventInsertDTO dto){
        Event entity = new Event(dto);
        entity = repo.save(entity);
        return new EventDTO(entity);
    }
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
