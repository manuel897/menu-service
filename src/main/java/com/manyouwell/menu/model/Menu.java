package com.manyouwell.menu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Menu {
    @JsonProperty("categories")
    private Category[] categories;

    public Category[] getCategories() {
        return categories;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }

    public Menu() {}

    public Menu(Category[] categories) {
        this.categories = categories;
    }

    public String ToString() {
        return String.format("Menu has %s items", categories.length);
    }
}
