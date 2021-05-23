package com.facens.pooii.ac1.ac1.dto;

import java.util.ArrayList;
import java.util.List;

import com.facens.pooii.ac1.ac1.entities.Ticket;

public class AttendInsertDTO extends BaseUserInsertDTO{
    
    private Double balance = 0.00 ;
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

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    public AttendInsertDTO(){

    }
}
