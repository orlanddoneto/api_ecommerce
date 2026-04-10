package com.orlando.ecommerce.services;

import com.orlando.ecommerce.entities.Role;
import com.orlando.ecommerce.entities.User;
import com.orlando.ecommerce.projections.UserDetailsProjection;
import com.orlando.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
