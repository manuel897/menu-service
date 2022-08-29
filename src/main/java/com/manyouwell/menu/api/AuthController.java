package com.manyouwell.menu.api;

import com.manyouwell.menu.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthController {

    @GetMapping(path = "/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello from menu service auth",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Category[] categoryReq) {
        // TODO validate request body
        return ResponseEntity.ok("not implemented");
    }
}
