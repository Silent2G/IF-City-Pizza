package com.yleaf.stas.if_citypizza.model;

public class ReservedPizza {
    private int id;
    private String title;
    private String price;
    private String imageSrc;
    private String diameter;
    private String description;
    private String weight;

    public ReservedPizza(int id, String title, String price, String imageSrc,
                         String diameter, String description, String weight) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageSrc = imageSrc;
        this.diameter = diameter;
        this.description = description;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getDiameter() {
        return diameter;
    }

    public String getDescription() {
        return description;
    }

    public String getWeight() {
        return weight;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
