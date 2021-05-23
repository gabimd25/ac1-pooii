package com.facens.pooii.ac1.ac1.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.facens.pooii.ac1.ac1.dto.PlaceDTO;
import com.facens.pooii.ac1.ac1.dto.PlaceInsertDTO;
import com.facens.pooii.ac1.ac1.dto.PlaceUpdateDTO;
import com.facens.pooii.ac1.ac1.entities.Place;
import com.facens.pooii.ac1.ac1.repositories.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlaceService{

    @Autowired
    private PlaceRepository repository;

    public Page<PlaceDTO> getPlaces(PageRequest pageRequest, String name, String address){
        Page<Place> list = repository.find(pageRequest, name, address);
        return list.map(e -> new PlaceDTO(e) );
    }

    public PlaceDTO getPlaceById(Long id){
        Optional<Place> op = repository.findById(id);
        Place place = op.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Place not found"));
        return new PlaceDTO(place);
    }

    public PlaceDTO insert(PlaceInsertDTO dto){
        //VALIDAÇÃO name e address não pode ser null
        if (dto.getName() == null || dto.getName().length() < 4 || dto.getName().length() > 50) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The name should be bigger than 4 letters and shorter than 50!");
        } 
        else if(dto.getAddress() == null || dto.getAddress().length() < 7){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The address should be at least 10 characters!");
        }
        else{
            Place entity = new Place(dto);
            entity = repository.save(entity);
            return new PlaceDTO(entity);
        }
    }
    public void delete(Long id) {
        //Um local não poderá ser removido se ele já foi usado por um evento
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }
    public PlaceDTO update(PlaceUpdateDTO eventUpdateDTO, Long id) {  
        if (eventUpdateDTO.getName() == null || eventUpdateDTO.getName().length() < 4 || eventUpdateDTO.getName().length() > 50) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "The name should be bigger than 4 letters and shorter than 50!");
        }
        else{
            try {
                Place entity = repository.getOne(id);
                entity.setName(eventUpdateDTO.getName());
                entity = repository.save(entity);
                return new PlaceDTO(entity);
    
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
            }
        }        
    }
}
