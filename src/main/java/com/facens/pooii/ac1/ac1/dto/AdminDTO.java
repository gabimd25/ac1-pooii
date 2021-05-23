package com.facens.pooii.ac1.ac1.dto;

import java.util.ArrayList;
import java.util.List;

import com.facens.pooii.ac1.ac1.entities.Admin;
import com.facens.pooii.ac1.ac1.entities.Event;

public class AdminDTO extends BaseUserDTO{
    
    private String phoneNumber;
    private List<Event> events = new ArrayList<>();
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public List<Event> getEvents() {
        return events;
    }
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public AdminDTO(){

    }
    public AdminDTO(Admin ad) {
        this.setId(ad.getId());
        this.setName(ad.getName());
        this.setEmailContact(ad.getEmailContact());
        this.phoneNumber = ad.getPhoneNumber();
    }
}
