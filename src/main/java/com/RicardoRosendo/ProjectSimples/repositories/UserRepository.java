package com.RicardoRosendo.ProjectSimples.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.RicardoRosendo.ProjectSimples.models.Users;



@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Transactional(readOnly = true)
    Users findByUserName(String userName);
}
