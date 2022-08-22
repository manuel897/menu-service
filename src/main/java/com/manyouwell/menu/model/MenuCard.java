package com.manyouwell.menu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class MenuCard {

    private static MenuCard singleInstance = null;
    @JsonProperty("categories")
    private Category[] categories;
    private LocalDateTime timestamp;

    public Category[] getCategories() {
        return categories;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    private MenuCard() {
        this.timestamp = LocalDateTime.now();
    }

    public static MenuCard MenuCard() {
        if(singleInstance == null) {
           singleInstance = new MenuCard();
        }
        return singleInstance;
    }


    public String ToString() {
        return String.format("Menu card has %s categories created at %s", categories.length, timestamp);
    }
}
