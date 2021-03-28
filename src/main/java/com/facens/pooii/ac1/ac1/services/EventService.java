package com.facens.pooii.ac1.ac1.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.facens.pooii.ac1.ac1.dto.EventDTO;
import com.facens.pooii.ac1.ac1.dto.EventInsertDTO;
import com.facens.pooii.ac1.ac1.dto.EventUpdateDTO;
import com.facens.pooii.ac1.ac1.entities.Event;
import com.facens.pooii.ac1.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    private EventRepository repo;

    public Page<EventDTO> getEvents(PageRequest pageRequest){
        Page<Event> list = repo.find(pageRequest);
        //List<EventDTO> listDTO = new ArrayList<>();

        /*for(Event e : list){
            EventDTO dto = new EventDTO(e.getId(), e.getName(),e.getDescription(), e.getPlace(),
                e.getStartDate(), e.getEndDate(), e.getStartTime(), e.getEndTime());
            listDTO.add(dto);
        }*/
        //return listDTO;
        return list.map(e -> new EventDTO(e));
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
        try{
            repo.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }
    public EventDTO update(EventUpdateDTO eventDTO, Long id) {
        try{
            Event event = repo.getOne(id);
            event.setDescription(eventDTO.getDescription());
            event.setPlace(eventDTO.getPlace());
            event.setStartTime(eventDTO.getStartTime());
            event.setEndTime(eventDTO.getEndTime());
            event.setEmailContact(eventDTO.getEmailContact());
            event = repo.save(event);
            return new EventDTO(event);
          }
          catch(EntityNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
          }
    }

}
