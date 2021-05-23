package com.facens.pooii.ac1.ac1.repositories;

import com.facens.pooii.ac1.ac1.entities.Place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>{

    @Query("SELECT e FROM Place e "+ "WHERE " + 
        "(LOWER(e.name) LIKE LOWER(CONCAT('%',:name,'%'))) AND " +
        "(LOWER (e.address) LIKE LOWER(CONCAT('%',:address,'%')))" )
    public Page<Place> find(Pageable pageRequest, String name, String address);
}
