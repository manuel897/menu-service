package com.manyouwell.menu.api;

import com.manyouwell.menu.dao.UserRepo;
import com.manyouwell.menu.model.Category;
import com.manyouwell.menu.model.Role;
import com.manyouwell.menu.model.User;
import com.manyouwell.menu.payload.request.SignupRequest;
import com.manyouwell.menu.payload.response.MessageResponse;
import com.manyouwell.menu.security.jwt.JwtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("auth")
public class AuthController {
    private static Logger logger = LogManager.getLogger(AuthController.class);
    AuthenticationManager authenticationManager;
    UserRepo userRepository;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

//    @CrossOrigin(origins = "*")
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody Category[] categoryReq) {
//        // TODO validate request body
//        return ResponseEntity.ok("not implemented");
//    }

    @Autowired
    public AuthController(UserRepo userRepository,
                          JwtUtils jwtUtils,
                          PasswordEncoder encoder,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        System.out.println("hereeeee!");
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
        "fakemail");
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles != null){
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = Role.ROLE_ADMIN;
                        break;
                    default:
                      throw new RuntimeException("Error: Role is not found.");
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Category[] categoryReq) {
        // TODO validate request body
        return ResponseEntity.ok("not implemented");
    }
}
