package com.kuang.amqmbrd.pojo;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    public Product(){}
    public Product(int id,String name,double price){
        this.id=id;
        this.name=name;
        this.price=price;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
