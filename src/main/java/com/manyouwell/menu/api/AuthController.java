package com.manyouwell.menu.api;

import com.manyouwell.menu.dao.UserRepo;
import com.manyouwell.menu.model.Role;
import com.manyouwell.menu.model.User;
import com.manyouwell.menu.payload.request.LoginRequest;
import com.manyouwell.menu.payload.request.SignupRequest;
import com.manyouwell.menu.payload.response.JwtResponse;
import com.manyouwell.menu.payload.response.MessageResponse;
import com.manyouwell.menu.security.jwt.JwtUtils;
import com.manyouwell.menu.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"https://www.lordstdpa.com", "https://lordstdpa.com"})
@RequestMapping("auth")
public class AuthController {
    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Value("${spring.data.mongodb.host}")
    public String usingHost;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping(path = "/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>(String.format("hello from menu service auth. Using host %s",this.usingHost),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginReq, HttpServletResponse response) {
        // TODO validate request body

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupReq) {
        // TODO validate request body
        if(userRepo.existsByUsername(signupReq.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken."));
        }
        if(userRepo.existsByEmail(signupReq.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already registered."));
        }
        User user = new User(signupReq.getUsername(),
                encoder.encode(signupReq.getPassword()),
                signupReq.getEmail());

        Set<String> strRoles = signupReq.getRoles();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach((strRole) -> {
            switch (strRole) {
                case "ROLE_MODERATOR" -> roles.add(Role.ROLE_MODERATOR);
                case "ROLE_ADMIN" -> roles.add(Role.ROLE_ADMIN);
                default -> throw new RuntimeException("Role is not found.");
            }
        });
        user.setRoles(roles);
        userRepo.save(user);
        return ResponseEntity.ok(new MessageResponse(String.format("User %s saved", user.getUsername())));
    }
}
