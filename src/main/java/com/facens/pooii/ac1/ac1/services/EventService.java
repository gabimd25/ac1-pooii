package com.facens.pooii.ac1.ac1.services;

import java.time.LocalDate;
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
    private EventRepository repository;

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, Double priceTicket,
            LocalDate startDate, String description){
        Page<Event> list = repository.find(pageRequest, name, priceTicket, startDate, description);
        return list.map(e -> new EventDTO(e) );
    }

    public EventDTO getEventById(Long id){
        Optional<Event> op = repository.findById(id);
        Event ev = op.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));
        return new EventDTO(ev);
    }

    public EventDTO insert(EventInsertDTO dto){
        //VALIDAÇÃO DAS HORAS
        //VALIDÇÃO INFORMAÇÕES - NAME, EMAILCONTACT
        //TEM QUE COLOCAR ID DO ADMIN
        if (dto.getStartDate().compareTo(dto.getEndDate()) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "The end date should be bigger than the start date!");
        } else {
            Event entity = new Event(dto);
            entity = repository.save(entity);
            return new EventDTO(entity);
        }     
    }
    public void delete(Long id) {
        //Um evento que já tenha ingressos vendidos não poderá ser removido
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }
    public EventDTO update(EventUpdateDTO eventUpdateDTO, Long id) {
        //VALIDAÇÃO PARA DATA NÃO SER ANTES DO DIA ATUAL
        //VALIDAÇÃO DAS HORAS
        //Não será possível alterar as informações do evento após a sua realização
        if (eventUpdateDTO.getStartDate().compareTo(eventUpdateDTO.getEndDate()) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "The end date should be bigger than the start date!");
        } 
        else
        {
            try {
                Event entity = repository.getOne(id);
                entity.setStartDate(eventUpdateDTO.getStartDate());
                entity.setEndDate(eventUpdateDTO.getEndDate());
                entity.setStartTime(eventUpdateDTO.getStartTime());
                entity.setEndTime(eventUpdateDTO.getEndTime());
                entity.setEmailContact(eventUpdateDTO.getEmailContact());
                entity.setAmountFreeTickets(eventUpdateDTO.getAmountFreeTickets());
                entity.setAmountPayedTickets(eventUpdateDTO.getAmountPayedTickets());
                entity.setPriceTicket(eventUpdateDTO.getPriceTicket());
                entity = repository.save(entity);
                return new EventDTO(entity);
    
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            }

        }
    }
}
