package com.RicardoRosendo.ProjectSimples.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.RicardoRosendo.ProjectSimples.models.Users;
import com.RicardoRosendo.ProjectSimples.repositories.UserRepository;
import com.RicardoRosendo.ProjectSimples.services.exceptions.DataBindViolationException;
import com.RicardoRosendo.ProjectSimples.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



   public Users findById(Long id){
        Optional<Users> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuario não Encontrado! id:"  + id + ", Class Type:" + Users.class.getName()));
   }

   @Transactional
   public Users create(Users obj){
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
   }

    @Transactional
   public Users update(Users obj){
        Users newObj = findById(obj.getId());
        newObj.setPassWord(obj.getPassWord());
        return this.userRepository.save(newObj);
   }

   public void delete(Long id){
        findById(id);
        try {
            this.delete(id);
            userRepository.deleteById(id);
        } catch (Exception e) {
           throw new DataBindViolationException("Erro ao Deletar o usuário com id: " + id + " com tarefas relacionadas!");
        }
   }

}
