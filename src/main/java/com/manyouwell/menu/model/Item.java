package com.manyouwell.menu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private int price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Item() {}

    @Override
    public String toString()  {
        return String.format("item[name='%s',price='%s ",name, price);
    }

}
