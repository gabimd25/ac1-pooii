package com.facens.pooii.ac1.ac1.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.facens.pooii.ac1.ac1.entities.Admin;
import com.facens.pooii.ac1.ac1.entities.Event;
import com.facens.pooii.ac1.ac1.entities.Place;

//Não recebe o email
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Double priceTicket;
    private Long freeTickectsSelled;
    private Long payedTickectsSelled;

    private List<Place> places = new ArrayList<>();
    private Admin admin;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
    public Double getPriceTicket() {
        return priceTicket;
    }
    public void setPriceTicket(Double priceTicket) {
        this.priceTicket = priceTicket;
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
    public List<Place> getPlaces() {
        return places;
    }
    public void setPlaces(List<Place> places) {
        this.places = places;
    }
    public Admin getAdmin() {
        return admin;
    }
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    public EventDTO(){
        
    }
    public EventDTO(Event ev){
        this.id = ev.getId();
        this.name = ev.getName();
        this.description = ev.getDescription();
        this.startDate = ev.getStartDate();
        this.endDate = ev.getEndDate();
        this.startTime = ev.getStartTime();
        this.endTime = ev.getEndTime(); 
        this.amountFreeTickets = ev.getAmountFreeTickets();
        this.amountPayedTickets = ev.getAmountPayedTickets();
        this.priceTicket = ev.getPriceTicket();
        this.freeTickectsSelled = ev.getFreeTickectsSelled();
        this.payedTickectsSelled = ev.getPayedTickectsSelled();
        this.places = ev.getPlaces();
        this.admin = ev.getAdmin();
    }
}
