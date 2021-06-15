package com.facens.pooii.ac1.ac1.controllers;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.net.URI;
import java.time.LocalDate;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.websocket.OnMessage;

import com.facens.pooii.ac1.ac1.dto.EventDTO;
import com.facens.pooii.ac1.ac1.dto.EventInsertDTO;
import com.facens.pooii.ac1.ac1.dto.EventUpdateDTO;
import com.facens.pooii.ac1.ac1.dto.TicketDTO;
import com.facens.pooii.ac1.ac1.dto.TicketGetDTO;
import com.facens.pooii.ac1.ac1.dto.TicketInsertDTO;
import com.facens.pooii.ac1.ac1.entities.Ticket;
import com.facens.pooii.ac1.ac1.services.EventService;

import org.apache.logging.log4j.message.Message;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService service;

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getEvents(
                        @RequestParam(value = "page",defaultValue = "0") Integer page,
                        @RequestParam(value = "linesPerPage",defaultValue = "6") Integer linesPerPage,
                        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                        @RequestParam(value = "name", defaultValue = "") String name,
                        @RequestParam(value = "priceTicket", defaultValue = "-1") Double priceTicket,
                        @RequestParam(value = "description", defaultValue = "") String description,
                        @RequestParam(value = "startDate", defaultValue = "2020-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate               
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        if(priceTicket == -1)
        {
            priceTicket = priceTicket.MAX_VALUE;
        }
        Page<EventDTO> list = service.getEvents(pageRequest, name.trim(), priceTicket, startDate, description.trim());

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id){
        EventDTO dto = service.getEventById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EventDTO> insert(@RequestBody EventInsertDTO insertDTO){
		EventDTO dto = service.insert(insertDTO); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@RequestBody EventUpdateDTO updateDTO, @PathVariable Long id) {
        EventDTO dto = service.update(updateDTO, id);
        return ResponseEntity.ok().body(dto);
    }
    //events/{id}/places/{id}
    @PostMapping("/{idEvent}/places/{idPlace}")
    public ResponseEntity<EventDTO> addEventPlace(@PathVariable("idEvent") Long idEvent, @PathVariable("idPlace") Long idPlace) {
        EventDTO dto = service.addEventPlace(idEvent, idPlace); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{idEvent}/places/{idPlace}")
    public ResponseEntity<Void> removeEventPlace(@PathVariable("idEvent") Long idEvent, @PathVariable("idPlace") Long idPlace){
        service.removeEventPlace(idEvent, idPlace);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tickets")
    public ResponseEntity<TicketDTO> insertTicket(@RequestBody TicketInsertDTO insertDTO, @PathVariable Long id){
        TicketDTO dto = service.insertTicket(insertDTO, id); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}/tickets")
    public ResponseEntity<Void> removeTicket(@RequestBody TicketInsertDTO insertDTO, @PathVariable Long id){
        service.removeTicket(insertDTO,id);
        return ResponseEntity.noContent().build();
    }
  
    @GetMapping("/{id}/tickets")
    public ResponseEntity<TicketGetDTO> getTickets(@PathVariable Long id){
        TicketGetDTO dto = service.getTickets(id);
        return ResponseEntity.ok(dto);
    }
    
}
