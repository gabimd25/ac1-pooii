package com.facens.pooii.ac1.ac1.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.facens.pooii.ac1.ac1.dto.TicketInsertDTO;

@Entity
@Table(name="TB_TICKET")
public class Ticket implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    private Instant date = Instant.now();
    
    private Double price;

    public TicketType type;

    @ManyToOne
    @JoinColumn(name="EVENT_ID")
    private Event event;
    
    @ManyToOne
    @JoinColumn(name="ATTEND_ID")
    private Attend attend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
        this.price = event.getPriceTicket();
    }

    public Attend getAttend() {
        return attend;
    }

    public void setAttend(Attend attend) {
        this.attend = attend;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ticket other = (Ticket) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Ticket(){

    }

    public Ticket(TicketInsertDTO dto){
        this.type = dto.getType();
    }
}
