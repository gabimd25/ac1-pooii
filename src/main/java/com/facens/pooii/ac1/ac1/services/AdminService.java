package com.facens.pooii.ac1.ac1.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.facens.pooii.ac1.ac1.dto.AdminDTO;
import com.facens.pooii.ac1.ac1.dto.AdminInsertDTO;
import com.facens.pooii.ac1.ac1.dto.AdminUpdateDTO;
import com.facens.pooii.ac1.ac1.entities.Admin;
import com.facens.pooii.ac1.ac1.repositories.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repository;

    public Page<AdminDTO> getAdmins(PageRequest pageRequest, String name, String phoneNumber, String emailContact){
        Page<Admin> list = repository.find(pageRequest, name, phoneNumber, emailContact);
        return list.map(e -> new AdminDTO(e) );
    }

    public AdminDTO getAdminById(Long id){
        Optional<Admin> op = repository.findById(id);
        Admin admin = op.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Admin not found"));
        return new AdminDTO(admin);
    }

    public AdminDTO insert(AdminInsertDTO dto){
        //VALIDAÇÃO -NENHUM PODE SER NULL
        if(dto.getName() == null || dto.getName().length() < 4 || dto.getName().length() > 50) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The name should be bigger than 4 letters and shorter than 50!");
        } 
        else if(dto.getEmailContact() == null || dto.getEmailContact().length() < 10){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The email contact should be at least 10 characters!");
        }
        else if(dto.getPhoneNumber() == null || dto.getPhoneNumber().length() < 8 ){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The phone number should be at least 8 characters!");
        }
        else{
            Admin entity = new Admin(dto);
            entity = repository.save(entity);
            return new AdminDTO(entity);
        }
        
    }
    public void delete(Long id) {
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }
    public AdminDTO update(AdminUpdateDTO UpdateDTO, Long id) {
        //VALIDAÇÃO -EMAIL E PHONE NUMBER NÃO PODE SER NULL
        if(UpdateDTO.getEmailContact() == null || UpdateDTO.getEmailContact().length() < 10){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The email contact should be at least 10 characters!");
        }
        else if(UpdateDTO.getPhoneNumber() == null || UpdateDTO.getPhoneNumber().length() < 8 ){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The phone number should be at least 8 characters!");
        }
        else{
            try{
                Admin entity = repository.getOne(id);
            entity.setEmailContact(UpdateDTO.getEmailContact());
            entity.setPhoneNumber(UpdateDTO.getPhoneNumber());
            entity = repository.save(entity);
            return new AdminDTO(entity);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
            }          
        }        
    }
}
