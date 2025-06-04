package com.noder.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noder.restapi.models.Role;

@Repository
public interface RoleRepostiory extends JpaRepository<Role, Long> {

    Role findByName(String name);
    
}
