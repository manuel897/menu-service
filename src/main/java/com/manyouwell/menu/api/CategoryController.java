package com.manyouwell.menu.api;

import com.manyouwell.menu.model.Category;
import com.manyouwell.menu.payload.response.MessageResponse;
import com.manyouwell.menu.service.MenuService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://lordstdpa.com")
//@CrossOrigin("http://localhost:3000")
@RequestMapping("category")
public class CategoryController {
    private static Logger logger = LogManager.getLogger(CategoryController.class);

    private final MenuService menuService;

    @Autowired
    public CategoryController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.menuService.findAll());
    }

    @PostMapping("/reset")
    public ResponseEntity<Category[]> resetMenu(@RequestBody Category[] categoryReq) {
        // TODO validate request body
        return ResponseEntity.ok(this.menuService.resetMenuCard(categoryReq));
    }

    @PostMapping("/add")
    public ResponseEntity<?> insertCategory(@RequestBody Category categoryReq) {
        // TODO validate request body
        if(this.menuService.existsByCategoryName(categoryReq.getName())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(String.format("Category %s already exists", categoryReq.getName())));
        }
        return ResponseEntity.ok(this.menuService.insertCategory(categoryReq));
    }
}