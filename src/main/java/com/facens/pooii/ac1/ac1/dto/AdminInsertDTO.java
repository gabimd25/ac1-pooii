package com.facens.pooii.ac1.ac1.dto;

public class AdminInsertDTO extends BaseUserInsertDTO{
    
    private String phoneNumber;
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AdminInsertDTO(){

    }
}
