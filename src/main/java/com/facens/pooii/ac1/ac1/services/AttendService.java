package com.facens.pooii.ac1.ac1.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.facens.pooii.ac1.ac1.dto.AttendDTO;
import com.facens.pooii.ac1.ac1.dto.AttendInsertDTO;
import com.facens.pooii.ac1.ac1.dto.AttendUpdateDTO;
import com.facens.pooii.ac1.ac1.entities.Attend;
import com.facens.pooii.ac1.ac1.repositories.AttendRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AttendService {

    @Autowired
    private AttendRepository repository;

    public Page<AttendDTO> getAttends(PageRequest pageRequest, String name, String emailContact){
        Page<Attend> list = repository.find(pageRequest, name, emailContact);
        return list.map(e -> new AttendDTO(e) );
    }

    public AttendDTO getAttendById(Long id){
        Optional<Attend> op = repository.findById(id);
        Attend attend = op.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found"));
        return new AttendDTO(attend);
    }

    public AttendDTO insert(AttendInsertDTO dto){
        Optional<Attend> op = repository.emailExist(dto.getEmailContact());

        //VALIDAÇÃO - NAME E EMAIL NÃO PODE SER NULL 
        if (dto.getName() == null || dto.getName().length() < 4 || dto.getName().length() > 50) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The name should be bigger than 4 letters and shorter than 50!");
        } 
        else if(dto.getEmailContact() == null || dto.getEmailContact().length() < 10){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The email contact should be at least 10 characters!");
        }
        else if(!op.isEmpty()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "This email have been used!");
        }
        else{
            Attend entity = new Attend(dto);
            entity.setBalance(0.00);
            entity = repository.save(entity);
            return new AttendDTO(entity);
        } 
        
    }
    public void delete(Long id) {
        //Verificar qual deve ser a validação
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found");
        }
    }
    public AttendDTO update(AttendUpdateDTO UpdateDTO, Long id) {
        //VALIDAÇÃO -EMAIL PODE SER NULL
        if(UpdateDTO.getEmailContact() == null || UpdateDTO.getEmailContact().length() < 10){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The email contact should be at least 10 characters!");
        }
        else{
            try{
                Attend entity = repository.getOne(id);
                entity.setEmailContact(UpdateDTO.getEmailContact());
                entity = repository.save(entity);
                return new AttendDTO(entity);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attend not found");
            }
            
        }
    }
}
