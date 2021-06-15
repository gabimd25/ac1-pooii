package com.facens.pooii.ac1.ac1.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.facens.pooii.ac1.ac1.dto.EventInsertDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TB_EVENT")
public class Event implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String emailContact;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Double priceTicket;
    private Long freeTickectsSelled= 0L;
    private Long payedTickectsSelled= 0L;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name="TB_EVENT_PLACE",
        joinColumns =  @JoinColumn(name="PLACE_ID"),
        inverseJoinColumns = @JoinColumn(name="EVENT_ID")
    )
    private List<Place> places = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="ADMIN_ID")
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
    public String getEmailContact() {
        return emailContact;
    }
    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
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
    public Admin getAdmin() {
        return admin;
    }
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    public List<Place> getPlaces() {
        return places;
    }
    public void setPlaces(List<Place> places) {
        this.places = places;
    }
    public void addPlace(Place place){
        this.places.add(place);
    }
    public void addTicket(Ticket ticket){
        this.tickets.add(ticket);
    }
    public void sellFreeTicket(){
        this.freeTickectsSelled += 1L;
    }
    public void sellPayedTicket(){
        this.payedTickectsSelled += 1L;
    }
    public void returnFreeTicket(){
        this.freeTickectsSelled -= 1L;
    }
    public void returnPayedTicket(){
        this.payedTickectsSelled -= 1L;
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
        Event other = (Event) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    public Event(EventInsertDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.emailContact = dto.getEmailContact();
        this.amountFreeTickets = dto.getAmountFreeTickets();
        this.amountPayedTickets = dto.getAmountPayedTickets();
        this.priceTicket = dto.getPriceTicket();
    }
    public Event(){
        
    }
}
