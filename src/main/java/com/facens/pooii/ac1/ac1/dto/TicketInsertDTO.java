package com.facens.pooii.ac1.ac1.dto;


import com.facens.pooii.ac1.ac1.entities.TicketType;

public class TicketInsertDTO {
        
    public TicketType type;

    public Long idAttend;
    
    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Long getIdAttend() {
        return idAttend;
    }

    public void setIdAttend(Long id){
        this.idAttend = id;
    }
    
    
}
