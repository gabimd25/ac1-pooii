package com.facens.pooii.ac1.ac1.dto;

import java.time.Instant;

import com.facens.pooii.ac1.ac1.entities.Attend;
import com.facens.pooii.ac1.ac1.entities.Event;
import com.facens.pooii.ac1.ac1.entities.Ticket;
import com.facens.pooii.ac1.ac1.entities.TicketType;

public class TicketDTO {

    private Long id;
   
    private Instant date;
    
    private Double price;

    public TicketType type;

    private Event event;
    
    private Attend attend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Attend getAttend() {
        return attend;
    }

    public void setAttend(Attend attend) {
        this.attend = attend;
    }

    public TicketDTO(Ticket ticket){
        this.id = ticket.getId();
        this.price = ticket.getPrice();
        this.type = ticket.getType();
        this.date = ticket.getDate();
        this.attend = ticket.getAttend();
        this.event = ticket.getEvent();
    }

}
