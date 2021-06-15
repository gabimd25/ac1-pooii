package com.facens.pooii.ac1.ac1.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.facens.pooii.ac1.ac1.dto.AttendInsertDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TB_ATTEND")
@PrimaryKeyJoinColumn(name="BASEUSER_ID")
public class Attend extends BaseUser{
    
    private Double balance;

    @OneToMany(mappedBy = "attend")
    @JsonIgnore
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
    public void addTicket(Ticket ticket){
        this.tickets.add(ticket);
    }
    public void addBalance(Double balance){
        this.balance += balance;
    }
    public Attend(){

    }
    public Attend(AttendInsertDTO dto) {
        this.setName(dto.getName());
        this.setEmailContact(dto.getEmailContact());
        this.balance = dto.getBalance();
    }
}
