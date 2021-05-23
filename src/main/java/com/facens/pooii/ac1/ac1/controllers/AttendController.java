package com.facens.pooii.ac1.ac1.controllers;

import java.net.URI;

import com.facens.pooii.ac1.ac1.dto.AttendDTO;
import com.facens.pooii.ac1.ac1.dto.AttendInsertDTO;
import com.facens.pooii.ac1.ac1.dto.AttendUpdateDTO;
import com.facens.pooii.ac1.ac1.services.AttendService;

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
@RequestMapping("/attends")
public class AttendController {
    @Autowired
    private AttendService service;

    @GetMapping
    public ResponseEntity<Page<AttendDTO>> getAttends(
                        @RequestParam(value = "page",defaultValue = "0") Integer page,
                        @RequestParam(value = "linesPerPage",defaultValue = "6") Integer linesPerPage,
                        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                        @RequestParam(value = "name", defaultValue = "") String name,
                        @RequestParam(value = "emailContact", defaultValue = "") String emailContact
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        Page<AttendDTO> list = service.getAttends(pageRequest, name.trim(), emailContact.trim());

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendDTO> getAttendById(@PathVariable Long id){
        AttendDTO dto = service.getAttendById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AttendDTO> insert(@RequestBody AttendInsertDTO insertDTO){
		AttendDTO dto = service.insert(insertDTO); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendDTO> update(@RequestBody AttendUpdateDTO updateDTO, @PathVariable Long id) {
        AttendDTO dto = service.update(updateDTO, id);
        return ResponseEntity.ok().body(dto);
    }
}
