package com.facens.pooii.ac1.ac1.dto;

public class BaseUserDTO {
    private Long id;
    private String name;
    private String emailContact;

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
    public String getEmailContact() {
        return emailContact;
    }
    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public BaseUserDTO(){
        
    }
}
