package com.manyouwell.menu.api;

import com.manyouwell.menu.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthController {
    private static Logger logger = LogManager.getLogger(AuthController.class);
    @GetMapping(path = "/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello from menu service auth",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String categoryReq) {
        // TODO validate request body
        logger.debug("login request received");
        return ResponseEntity.ok("not implemented");
    }
}
