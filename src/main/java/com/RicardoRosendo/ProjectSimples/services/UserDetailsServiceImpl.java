package com.RicardoRosendo.ProjectSimples.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.RicardoRosendo.ProjectSimples.Security.UserSpringSecurity;
import com.RicardoRosendo.ProjectSimples.models.Users;
import com.RicardoRosendo.ProjectSimples.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users user = this.userRepository.findByUserName(username);
       if(Objects.isNull(user)){
           throw new UsernameNotFoundException("Usuario não encontrado! : " + username);
       }
       return new UserSpringSecurity(user.getId(), user.getUserName(), user.getPassWord(), user.getProfiles());
    }
    

}
