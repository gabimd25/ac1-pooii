package com.facens.pooii.ac1.ac1.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.facens.pooii.ac1.ac1.dto.AdminInsertDTO;

@Entity
@Table(name="TB_ADMIN")
@PrimaryKeyJoinColumn(name="BASEUSER_ID")
public class Admin extends BaseUser{
    
    private String phoneNumber;

    @OneToMany(mappedBy = "admin")
    private List<Event> events = new ArrayList<>();
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Admin(){
        
    }
    public Admin(AdminInsertDTO dto) {
        this.setName(dto.getName());
        this.setEmailContact(dto.getEmailContact());
        this.phoneNumber = dto.getPhoneNumber();
    }

}
