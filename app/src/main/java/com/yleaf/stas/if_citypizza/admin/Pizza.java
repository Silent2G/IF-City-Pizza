package com.yleaf.stas.if_citypizza.admin;

public class Pizza {

    private int id;
    private String title;
    private String weight;
    private String priceStandart;
    private String priceLarge;
    private String diameterStandart;
    private String diameterLarge;
    private String description;
    private String imageSrc;

    public Pizza() {}

    public Pizza(int id, String title, String weight,
                 String priceStandart, String priceLarge,
                 String diameterStandart, String diameterLarge,
                 String description, String imageSrc) {

        this.id = id;
        this.title = title;
        this.weight = weight;
        this.priceStandart = priceStandart;
        this.priceLarge = priceLarge;
        this.diameterStandart = diameterStandart;
        this.diameterLarge = diameterLarge;
        this.description = description;
        this.imageSrc = imageSrc;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPriceStandart() {
        return priceStandart;
    }

    public String getPriceLarge() {
        return priceLarge;
    }

    public String getDiameterStandart() {
        return diameterStandart;
    }

    public String getDiameterLarge() {
        return diameterLarge;
    }

    public String getDescription() {
        return description;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getWeight() {
        return weight;
    }
}
