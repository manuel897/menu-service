package com.manyouwell.menu.api;

import com.manyouwell.menu.dao.UserRepo;
import com.manyouwell.menu.model.Category;
import com.manyouwell.menu.model.Role;
import com.manyouwell.menu.model.User;
import com.manyouwell.menu.payload.request.LoginRequest;
import com.manyouwell.menu.payload.request.SignupRequest;
import com.manyouwell.menu.payload.response.MessageResponse;
import com.manyouwell.menu.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("auth")
public class AuthController {
    private static Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping(path = "/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello from menu service auth",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginReq) {
        // TODO validate request body
        logger.debug("login request received {}",loginReq);
        return ResponseEntity.ok("not implemented");
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupReq) {
        // TODO validate request body
        logger.debug("signup request received for role: {}",signupReq.getRoles());
        if(userRepo.existsByUsername(signupReq.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken."));
        }
        if(userRepo.existsByEmail(signupReq.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already registered."));
        }
        User user = new User(signupReq.getUsername(),
                passwordEncoder.encode(signupReq.getPassword()),
                signupReq.getEmail());
        Set<String> strRoles = signupReq.getRoles();
        Set<Role> roles = new HashSet<Role>();

        strRoles.forEach((strRole) -> {
            switch (strRole) {
                case "ROLE_MODERATOR":
                    roles.add(Role.ROLE_MODERATOR);
                    break;
                case "ROLE_ADMIN":
                    roles.add(Role.ROLE_ADMIN);
                    break;
                default:
                    throw new RuntimeException("Role is not found.");
            }
        });
        logger.debug(user.getPassword());
        user.setRoles(roles);
        userRepo.save(user);
        return ResponseEntity.ok(new MessageResponse(String.format("User %s saved", user.getUsername())));
    }
}
