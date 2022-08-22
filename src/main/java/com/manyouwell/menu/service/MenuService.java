package com.manyouwell.menu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manyouwell.menu.dao.CategoryRepo;
import com.manyouwell.menu.model.Category;
import com.manyouwell.menu.model.Menu;

import com.manyouwell.menu.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class MenuService {
    private final CategoryRepo categoryRepo;

    @Autowired
    public MenuService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @PostConstruct
    public void init() {
        this.loadMenuData();
    }

    private void loadMenuData() {
        try {
            this.categoryRepo.deleteAll();

            ClassPathResource menuDataResource = new ClassPathResource("static/menu.json");
            if(menuDataResource.exists()) {
                File menuDataFile = menuDataResource.getFile();
                ObjectMapper objectMapper = new ObjectMapper();
                Menu menu = objectMapper.readValue(menuDataFile, Menu.class);

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
}
