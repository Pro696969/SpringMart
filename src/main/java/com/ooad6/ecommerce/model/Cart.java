package com.ooad6.ecommerce.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Cart")
public class Cart {
    private String id;
    private String Name;
    private int Cost;
    private int Qty;
    private String Description;

    public Cart(String id, String name, int cost, int i) {

    }

    public Cart(String id, String name, int cost, int qty, String description) {
        this.id = id;
        this.Name = name;
        this.Cost = cost;
        this.Qty = qty;
        this.Description = description;
    }


    @Override
    public String toString() {
        return "Items{" +
                "Name='" + Name + '\'' +
                ", Cost=" + Cost +
                ", Qty=" + Qty +
                ", Description='" + Description + '\'' +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
