package com.facens.pooii.ac1.ac1.controllers;

import java.net.URI;

import com.facens.pooii.ac1.ac1.dto.PlaceDTO;
import com.facens.pooii.ac1.ac1.dto.PlaceInsertDTO;
import com.facens.pooii.ac1.ac1.dto.PlaceUpdateDTO;
import com.facens.pooii.ac1.ac1.services.PlaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/places")
public class PlaceController {
    @Autowired
    private PlaceService service;

    @GetMapping
    public ResponseEntity<Page<PlaceDTO>> getPlaces(
                        @RequestParam(value = "page",defaultValue = "0") Integer page,
                        @RequestParam(value = "linesPerPage",defaultValue = "6") Integer linesPerPage,
                        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                        @RequestParam(value = "name", defaultValue = "") String name,
                        @RequestParam(value = "address", defaultValue = "") String address
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        Page<PlaceDTO> list = service.getPlaces(pageRequest, name.trim(), address.trim());

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable Long id){
        PlaceDTO dto = service.getPlaceById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PlaceDTO> insert(@RequestBody PlaceInsertDTO insertDTO){
		PlaceDTO dto = service.insert(insertDTO); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDTO> update(@RequestBody PlaceUpdateDTO updateDTO, @PathVariable Long id) {
        PlaceDTO dto = service.update(updateDTO, id);
        return ResponseEntity.ok().body(dto);
    }
}
