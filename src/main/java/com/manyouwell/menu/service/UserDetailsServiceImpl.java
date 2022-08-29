package com.manyouwell.menu.service;

import com.manyouwell.menu.dao.UserRepo;
import com.manyouwell.menu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsImpl userDetailsImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() ->
             new UsernameNotFoundException(String.format("Username %s not found", username)
        ));
        return UserDetailsImpl.build(user);
    }
}
