package com.example.homeworkseven2.repositories;

import com.example.homeworkseven2.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findRoleByName(String name);

}

