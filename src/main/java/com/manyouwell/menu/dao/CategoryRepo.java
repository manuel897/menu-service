package com.manyouwell.menu.dao;

import com.manyouwell.menu.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends MongoRepository<Category, String> {
    Boolean existsByName(String name);
}
