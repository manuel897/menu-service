package com.manyouwell.menu.model;

import org.springframework.data.annotation.Id;

import java.util.List;


public class Category {
    @Id
    private String name;
    private Item[] items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public Category(String name, Item[] items) {
        this.name = name;
        this.items = items;
    }

    public String toString()  {
        return String.format("%s has %s items",name, items.length);
    }
}
