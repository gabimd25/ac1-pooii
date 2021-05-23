package com.facens.pooii.ac1.ac1.repositories;

import com.facens.pooii.ac1.ac1.entities.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{

    @Query("SELECT e FROM Admin e "+ "WHERE " + 
        "(LOWER(e.name) LIKE LOWER(CONCAT('%',:name,'%'))) AND " +
        "(LOWER(e.phoneNumber) LIKE LOWER(CONCAT('%',:phoneNumber,'%'))) AND " +
        "(LOWER (e.emailContact) LIKE LOWER(CONCAT('%',:emailContact,'%')))" )
    public Page<Admin> find(Pageable pageRequest, String name, String phoneNumber, String emailContact);
}
