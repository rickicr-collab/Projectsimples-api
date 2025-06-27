package com.RicardoRosendo.ProjectSimples.Security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.RicardoRosendo.ProjectSimples.models.Enums.ProfileEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSpringSecurity implements UserDetails {

    private Long id;
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSpringSecurity(Long id, String userName, String password, Set<ProfileEnum> profileEnum) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.authorities = profileEnum.stream().map(x -> new SimpleGrantedAuthority(x.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(ProfileEnum profileEnum){
        return getAuthorities().contains(new SimpleGrantedAuthority(profileEnum.getDescription()));
    }



}
