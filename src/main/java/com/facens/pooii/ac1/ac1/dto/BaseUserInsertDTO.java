package com.facens.pooii.ac1.ac1.dto;

public class BaseUserInsertDTO {
    private String name;
    private String emailContact;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmailContact() {
        return emailContact;
    }
    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public BaseUserInsertDTO(){
        
    }
}
