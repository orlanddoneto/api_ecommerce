package com.orlando.ecommerce.services;

import com.orlando.ecommerce.entities.DTOs.UserDTO;
import com.orlando.ecommerce.entities.Role;
import com.orlando.ecommerce.entities.User;
import com.orlando.ecommerce.projections.UserDetailsProjection;
import com.orlando.ecommerce.repositories.UserRepository;
import com.orlando.ecommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> user = repository.searchUserAndRolesByEmail(username);
        if (user.size() == 0){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        User user_temp = new User();
        user_temp.setEmail(user.get(0).getUsername());
        user_temp.setPassword(user.get(0).getPassword());
        for (UserDetailsProjection obj : user){
            user_temp.addRole(new Role(obj.getAuthority(), obj.getRoleId()));
        }
        return user_temp;
    }

    protected User authenticated(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");

            return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        }

        catch (Exception e){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }


    }

    @Transactional(readOnly = true)
    public UserDTO getMe(){
        User user = authenticated();
        return new UserDTO(user);
    }
}
