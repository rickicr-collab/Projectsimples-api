package com.RicardoRosendo.ProjectSimples.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RicardoRosendo.ProjectSimples.models.Users;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {}
