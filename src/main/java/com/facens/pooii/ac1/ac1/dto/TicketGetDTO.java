package com.facens.pooii.ac1.ac1.dto;

import java.util.ArrayList;
import java.util.List;

import com.facens.pooii.ac1.ac1.entities.Attend;
import com.facens.pooii.ac1.ac1.entities.Event;
import com.facens.pooii.ac1.ac1.entities.Ticket;

public class TicketGetDTO {

    private List<Attend> freeTickets = new ArrayList<>();
    private List<Attend> payedTickets = new ArrayList<>();
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Long freeTickectsSelled;
    private Long payedTickectsSelled;

    public List<Attend> getFreeTickets() {
        return freeTickets;
    }
    public void setFreeTickets(List<Attend> freeTickets) {
        this.freeTickets = freeTickets;
    }
    public List<Attend> getPayedTickets() {
        return payedTickets;
    }
    public void setPayedTickets(List<Attend> payedTickets) {
        this.payedTickets = payedTickets;
    }
    public Long getAmountFreeTickets() {
        return amountFreeTickets;
    }
    public void setAmountFreeTickets(Long amountFreeTickets) {
        this.amountFreeTickets = amountFreeTickets;
    }
    public Long getAmountPayedTickets() {
        return amountPayedTickets;
    }
    public void setAmountPayedTickets(Long amountPayedTickets) {
        this.amountPayedTickets = amountPayedTickets;
    }
    public Long getFreeTickectsSelled() {
        return freeTickectsSelled;
    }
    public void setFreeTickectsSelled(Long freeTickectsSelled) {
        this.freeTickectsSelled = freeTickectsSelled;
    }
    public Long getPayedTickectsSelled() {
        return payedTickectsSelled;
    }
    public void setPayedTickectsSelled(Long payedTickectsSelled) {
        this.payedTickectsSelled = payedTickectsSelled;
    }
    public void addAttendFree(Attend attend){
        this.freeTickets.add(attend);
    }
    public void addAttendPayed(Attend attend){
        this.payedTickets.add(attend);
    }
    public TicketGetDTO(){

    }
    public TicketGetDTO(Event ev){
        this.amountFreeTickets = ev.getAmountFreeTickets();
        this.amountPayedTickets = ev.getAmountPayedTickets();
        this.freeTickectsSelled = ev.getFreeTickectsSelled();
        this.payedTickectsSelled = ev.getPayedTickectsSelled();
    }
    public TicketGetDTO(Ticket ticket){
        this.freeTickets.add(ticket.getAttend());
    }

}