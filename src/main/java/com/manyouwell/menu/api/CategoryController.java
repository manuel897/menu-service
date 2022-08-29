package com.manyouwell.menu.api;

import com.manyouwell.menu.model.Category;
import com.manyouwell.menu.service.MenuService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    private static Logger logger = LogManager.getLogger(CategoryController.class);
    private final MenuService menuService;

    @Autowired
    public CategoryController(MenuService menuService) {
        this.menuService = menuService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Category> findAll() {

        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
        return this.menuService.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/reset")
    public ResponseEntity<Category[]> resetMenu(@RequestBody Category[] categoryReq) {
        // TODO validate request body
        return ResponseEntity.ok(this.menuService.resetMenuCard(categoryReq));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add")
    public ResponseEntity<Category> insertCategory(@RequestBody Category categoryReq) {
        // TODO validate request body
        return ResponseEntity.ok(this.menuService.insertCategory(categoryReq));
    }
}