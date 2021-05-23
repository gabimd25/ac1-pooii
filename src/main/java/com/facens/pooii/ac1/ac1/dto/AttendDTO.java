package com.facens.pooii.ac1.ac1.dto;

import java.util.ArrayList;
import java.util.List;

import com.facens.pooii.ac1.ac1.entities.Attend;
import com.facens.pooii.ac1.ac1.entities.Ticket;

public class AttendDTO extends BaseUserDTO{
    
    private Double balance;
    private List<Ticket> tickets = new ArrayList<>();
    
    
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setEvents(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public AttendDTO(){

    }
    public AttendDTO(Attend at) {
        this.setId(at.getId());
        this.setName(at.getName());
        this.setEmailContact(at.getEmailContact());
        this.balance = at.getBalance();
    }
}
