package com.manyouwell.menu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manyouwell.menu.dao.CategoryRepo;
import com.manyouwell.menu.model.Category;
import com.manyouwell.menu.model.MenuCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuService {
    private final CategoryRepo categoryRepo;

    @Autowired
    public MenuService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

//    @PostConstruct
//    public void init() {
//        this.loadMenuData();
//    }

    private void loadMenuData() {
        try {
            this.categoryRepo.deleteAll();

            ClassPathResource menuDataResource = new ClassPathResource("static/menu.json");
            if(menuDataResource.exists()) {
                File menuDataFile = menuDataResource.getFile();
                ObjectMapper objectMapper = new ObjectMapper();
                Category[] categories = objectMapper.readValue(menuDataFile, Category[].class);

                MenuCard menu = MenuCard.MenuCard();
                menu.setTimestamp(LocalDateTime.now());
                menu.setCategories(categories);

                for (Category c : menu.getCategories()
                     ) {
                    this.categoryRepo.insert(c);
                }
            }
        } catch (Exception e) {
            // TODO properly handle exception
            e.printStackTrace();
        }
    }

    public List<Category> findAll() { return this.categoryRepo.findAll(); }
    public Category insert(Category category) { return this.categoryRepo.insert(category); }

    public Category[] resetMenuCard(Category[] categories) {
        this.categoryRepo.deleteAll();
        for(Category c: categories) {
            this.categoryRepo.insert(c);
        }
        MenuCard menucard = MenuCard.MenuCard();
        menucard.setCategories(categories);
        menucard.setTimestamp(LocalDateTime.now());

        // System.out.println(String.format("LOG: %s", menucard.ToString()));
        return categories;
    }

    public Category insertCategory(Category c) {
        this.categoryRepo.insert(c);
        return c;
    }

    public Boolean existsByCategoryName(String name) {
        return this.categoryRepo.existsByName(name);
    }
}
