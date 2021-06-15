package com.facens.pooii.ac1.ac1.repositories;

import java.util.Optional;

import com.facens.pooii.ac1.ac1.entities.Attend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository<Attend, Long>{

    @Query("SELECT e FROM Attend e "+ "WHERE " + 
        "(LOWER(e.name) LIKE LOWER(CONCAT('%',:name,'%'))) AND " +
        "(LOWER (e.emailContact) LIKE LOWER(CONCAT('%',:emailContact,'%')))" )
    public Page<Attend> find(Pageable pageRequest, String name, String emailContact);

    @Query("SELECT e FROM Attend e "+"WHERE "+
        "e.emailContact = :emailContact")
    public Optional<Attend> emailExist(String emailContact);
}
