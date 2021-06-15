package com.facens.pooii.ac1.ac1.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.DoubleToIntFunction;

import javax.persistence.EntityNotFoundException;

import com.facens.pooii.ac1.ac1.dto.EventDTO;
import com.facens.pooii.ac1.ac1.dto.EventInsertDTO;
import com.facens.pooii.ac1.ac1.dto.EventUpdateDTO;
import com.facens.pooii.ac1.ac1.dto.TicketDTO;
import com.facens.pooii.ac1.ac1.dto.TicketGetDTO;
import com.facens.pooii.ac1.ac1.dto.TicketInsertDTO;
import com.facens.pooii.ac1.ac1.entities.Admin;
import com.facens.pooii.ac1.ac1.entities.Attend;
import com.facens.pooii.ac1.ac1.entities.Event;
import com.facens.pooii.ac1.ac1.entities.Place;
import com.facens.pooii.ac1.ac1.entities.Ticket;
import com.facens.pooii.ac1.ac1.entities.TicketType;
import com.facens.pooii.ac1.ac1.repositories.AdminRepository;
import com.facens.pooii.ac1.ac1.repositories.AttendRepository;
import com.facens.pooii.ac1.ac1.repositories.EventRepository;
import com.facens.pooii.ac1.ac1.repositories.PlaceRepository;
import com.facens.pooii.ac1.ac1.repositories.TicketRepository;

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
    
    @Autowired
    private PlaceRepository repositoryPlace;

    @Autowired
    private TicketRepository repositoryTicket;

    @Autowired
    private AttendRepository repositoryAttend;
    
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
            EventDTO event = getEventById(id);
            Long selled;
            selled = event.getFreeTickectsSelled() + event.getPayedTickectsSelled();
            if(selled > 0){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "this event already has tickets sold");
            }
            else{
               repository.deleteById(id); 
            }            
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

                if(entity.getEndDate().compareTo(date)<0){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "this event cannot be changed because it's over!");
                }

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
    public EventDTO addEventPlace(Long idEvent, Long idPlace) {
        //Um evento que já tenha ingressos vendidos não poderá ser removido
        
        Optional<Event> ev = repository.findById(idEvent);
        Event event = ev.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));

        Optional<Place> pl = repositoryPlace.findById(idPlace);
        Place place = pl.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Place not found"));
        
        List<Event> events = place.getEvents();
        Boolean disponivel=true;
        LocalDateTime startEvent = event.getStartDate().atTime(event.getStartTime());
        LocalDateTime endEvent = event.getEndDate().atTime(event.getEndTime());

        for(Event e : events){
            LocalDateTime startE = e.getStartDate().atTime(e.getStartTime());
            LocalDateTime endE = e.getEndDate().atTime(e.getEndTime());
            if(startEvent.isAfter(startE) && startEvent.isBefore(endE)){
                disponivel = false;
            }
            else if(endEvent.isAfter(startE) && endEvent.isBefore(endE)){
                disponivel = false;
            }
            else if(startEvent.isBefore(startE) && endEvent.isAfter(endE)){
                disponivel = false;
            }
            else if(startEvent.isEqual(startE) || endEvent.isEqual(endE)){
                disponivel = false;
            }
        }
        if(disponivel == true){
            event.getPlaces().add(place);
            place.getEvents().add(event);
            //event.addPlace(place);
            //place.addEvent(event);

            event = repository.save(event);
            //place = repositoryPlace.save(place);
            return new EventDTO(event);
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Place unavailable!");
        }
        
    }
    public void removeEventPlace(Long idEvent, Long idPlace) {
        Optional<Event> ev = repository.findById(idEvent);
        Event event = ev.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));

        Optional<Place> pl = repositoryPlace.findById(idPlace);
        Place place = pl.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Place not found"));

        try{
            event.getPlaces().remove(place);
            place.getEvents().remove(event);

            event = repository.save(event);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error, try again!");
        }
    }
    public TicketDTO insertTicket(TicketInsertDTO insertDTO, Long id){
        LocalDateTime date = LocalDateTime.now();

        Optional<Event> ev = repository.findById(id);
        Event event = ev.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));

        LocalDateTime endE = event.getEndDate().atTime(event.getEndTime());

        if(endE.compareTo(date)<0 ){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "This event is over");
        }

        Optional<Attend> at = repositoryAttend.findById(insertDTO.getIdAttend());
        Attend attend = at.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Attend not found"));
        Ticket ticket = new Ticket(insertDTO);
        ticket.setEvent(event);
        ticket.setAttend(attend);

        if(insertDTO.getType()==TicketType.FREE){
            if(event.getFreeTickectsSelled()<event.getAmountFreeTickets()){
                event.sellFreeTicket();
                attend.addTicket(ticket);

                ticket = repositoryTicket.save(ticket);
                attend = repositoryAttend.save(attend);
                event = repository.save(event);
            }
            else{
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Free Tickets unavailable");
            }
        }
        else{
            if(event.getPayedTickectsSelled()<event.getAmountPayedTickets()){
                event.sellPayedTicket();
                attend.addTicket(ticket);

                ticket = repositoryTicket.save(ticket);
                attend = repositoryAttend.save(attend);
                event = repository.save(event);
            }
            else{
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payed Tickets unavailable");
            }
        }

        return new TicketDTO(ticket);
    }

    public void removeTicket(TicketInsertDTO insertDTO, Long id) {
        LocalDateTime date = LocalDateTime.now();
        Optional<Event> ev = repository.findById(id);
        Event event = ev.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));

        LocalDateTime startE = event.getStartDate().atTime(event.getStartTime());

        Optional<Attend> at = repositoryAttend.findById(insertDTO.getIdAttend());
        Attend attend = at.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Attend not found"));
        
        if(startE.compareTo(date)<= 0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This event started");
        }

        List<Ticket> tickets = attend.getTickets();
        Long idTicket=null;
        for(Ticket tic : tickets){
            if(tic.getEvent().getId()==id){
                idTicket = tic.getId();
            }
        }
        try{
                Optional<Ticket> tic = repositoryTicket.findById(idTicket);
                Ticket ticket = tic.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Ticket not found"));
                
                event.getTickets().remove(ticket);
                attend.getTickets().remove(ticket);

                if(ticket.getType()==TicketType.FREE){
                    event.returnFreeTicket();
                }
                else{
                    event.returnPayedTicket();
                    attend.addBalance(ticket.getPrice());
                }

                repositoryTicket.delete(ticket);
                event = repository.save(event);
            }
            catch(EmptyResultDataAccessException e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error, try again!");
            }
        
    }

    public TicketGetDTO getTickets(Long id){
        Optional<Event> op = repository.findById(id);
        Event ev = op.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Event not found"));

        TicketGetDTO dto = new TicketGetDTO(ev);
        
        for(Ticket ticket : ev.getTickets()){
            if(ticket.getType() == TicketType.FREE){
                dto.addAttendFree(ticket.getAttend());
            }
            else{
                dto.addAttendPayed(ticket.getAttend());
            }
        }
        return dto;
    }
    /*
    VERIFICAR PORQUE OS EVENTS DO DATA.SQL NAO ESTAO SALVANDO*/
}
