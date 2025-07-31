package com.RicardoRosendo.ProjectSimples.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RicardoRosendo.ProjectSimples.models.Task;
import com.RicardoRosendo.ProjectSimples.models.Projection.TaskProjetion;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<TaskProjetion> findByUser_Id(Long id);

    //@Query("SELECT t FROM Task t WHERE t.user.id = :id")
    //List<Task> findByUserId(@Param("id") Long id);

    //@Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    //List<Task> findByUserId(@Param("id") Long id);

}
