package com.manyouwell.menu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private String price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Item(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Item() {}

    @Override
    public String toString()  {
        return String.format("item[name='%s',price='%s ",name, price);
    }

}
