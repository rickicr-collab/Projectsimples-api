package com.RicardoRosendo.ProjectSimples.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.RicardoRosendo.ProjectSimples.Security.UserSpringSecurity;
import com.RicardoRosendo.ProjectSimples.models.Task;
import com.RicardoRosendo.ProjectSimples.models.Users;
import com.RicardoRosendo.ProjectSimples.models.Enums.ProfileEnum;
import com.RicardoRosendo.ProjectSimples.repositories.TaskRepository;
import com.RicardoRosendo.ProjectSimples.services.exceptions.DataBindViolationException;
import com.RicardoRosendo.ProjectSimples.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada com Id : " + id + ", Class Type: " + Task.class.getName()));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasTask(userSpringSecurity, task)) {
            throw new AuthorizationDeniedException("Acesso Negado!");
        }

        return task;
    }

    public List<Task> findAllByUser() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationDeniedException("Acesso Negado!");
        }
        List<Task> tasks = this.taskRepository.findByUser_Id(userSpringSecurity.getId());
        return tasks;
    }

    @Transactional
    public Task create(Task obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationDeniedException("Acesso Negado!");
        }
        Users user = userService.findById(userSpringSecurity.getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindViolationException(
                    "Não é possivel deletar a tarefa com id: " + id + " possui usuários relacionados!");
        }
    }

    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }

}
