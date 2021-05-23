package com.facens.pooii.ac1.ac1.dto;

import java.util.ArrayList;
import java.util.List;

import com.facens.pooii.ac1.ac1.entities.Event;

public class PlaceInsertDTO {

    private String name;
    private String address;
    private List<Event> events = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public PlaceInsertDTO(){

    }

    
}
