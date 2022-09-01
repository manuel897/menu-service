package com.manyouwell.menu.service;

import com.manyouwell.menu.api.AuthController;
import com.manyouwell.menu.dao.UserRepo;
import com.manyouwell.menu.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("loadUser {}", username);
        User user = userRepo.findByUsername(username).orElseThrow(() ->
             new UsernameNotFoundException(String.format("Username %s not found", username)
        ));
        return UserDetailsImpl.build(user);
    }
}
