package com.manyouwell.menu.dao;

import com.manyouwell.menu.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepo extends MongoRepository<Category, String> {

}
