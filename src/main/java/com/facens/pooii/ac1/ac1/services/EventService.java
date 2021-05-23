package com.facens.pooii.ac1.ac1.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.facens.pooii.ac1.ac1.dto.EventDTO;
import com.facens.pooii.ac1.ac1.dto.EventInsertDTO;
import com.facens.pooii.ac1.ac1.dto.EventUpdateDTO;
import com.facens.pooii.ac1.ac1.entities.Admin;
import com.facens.pooii.ac1.ac1.entities.Event;
import com.facens.pooii.ac1.ac1.repositories.AdminRepository;
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

    @Autowired
    private AdminRepository repositoryAdmin;
    
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
        LocalDate date = LocalDate.now();
        //VALIDAÇÃO DAS HORAS
        if (dto.getStartDate().compareTo(dto.getEndDate()) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The end date should be bigger than the start date!");
        }
        else if (dto.getStartDate().compareTo(dto.getEndDate()) == 0 && dto.getStartTime().compareTo(dto.getEndTime()) >= 0 ){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The end time should be bigger than the start time at the same day!");
        }
        else if(dto.getEndDate().compareTo(date) < 0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The end date should be bigger or equal to today!");
        }
        //VALIDAÇÃO INFORMAÇÕES - NAME, EMAILCONTACT
        else if (dto.getName() == null || dto.getName().length() < 4 || dto.getName().length() > 50) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The name should be bigger than 4 letters and shorter than 50!");
        } 
        else if(dto.getEmailContact() == null || dto.getEmailContact().length() < 10){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The email contact should be at least 10 characters!");
        }
        else if(dto.getAmountFreeTickets() == null || dto.getAmountPayedTickets() == null 
                    || dto.getAmountFreeTickets() < 0 || dto.getAmountPayedTickets() < 0 ){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The amount free tickets and payed tickets should be bigger or equal to 0!");
        }
        else if(dto.getPriceTicket() == null || dto.getPriceTicket() < 0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The ticket´s price should be bigger or equal to 0!");
        }
        //TEM QUE COLOCAR ID DO ADMIN
        else if(dto.getIdAdmin() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The id should be a number!");
        }        
        else {
            Long id = dto.getIdAdmin();
            Optional<Admin> op = repositoryAdmin.findById(id);
            Admin admin = op.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Admin not found"));

            Event entity = new Event(dto);
            entity.setAdmin(admin);

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
        LocalDate date = LocalDate.now();
        //VALIDAÇÃO PARA DATA NÃO SER ANTES DO DIA ATUAL
        if (eventUpdateDTO.getStartDate().compareTo(eventUpdateDTO.getEndDate()) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "The end date should be bigger than the start date!");
        } 
        //VALIDAÇÃO DAS HORAS
        else if (eventUpdateDTO.getStartDate().compareTo(eventUpdateDTO.getEndDate()) == 0 && eventUpdateDTO.getStartTime().compareTo(eventUpdateDTO.getEndTime()) >= 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "The end time should be bigger than the start time at the same day!");
        } 
        //Não será possível alterar as informações do evento após a sua realização
        else if(eventUpdateDTO.getEndDate().compareTo(date) < 0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The end date should be bigger or equal to today!");
        }
        else if(eventUpdateDTO.getEmailContact() == null || eventUpdateDTO.getEmailContact().length() < 10){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The email contact should be at least 10 characters!");
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
