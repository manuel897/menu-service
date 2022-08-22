package com.manyouwell.menu.service;

import com.manyouwell.menu.dao.CategoryRepo;
import com.manyouwell.menu.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private CategoryRepo categoryRepo;

    @Autowired
    public MenuService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    };

    public List<Category> findAll() { return this.categoryRepo.findAll(); }
    public Category insert(Category category) { return this.categoryRepo.insert(category); }
}
