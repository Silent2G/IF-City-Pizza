package com.yleaf.stas.if_citypizza;

public class Pizza {

    private String title;
    private String priceStandart;
    private String priceLarge;
    private String diameterStandart;
    private String diameterLarge;
    private String description;
    private String imageSrc;
    private String imagePath;

    public Pizza(String title, String priceStandart, String priceLarge,
                 String diameterStandart, String diameterLarge,
                 String description, String imageSrc, String imagePath) {

        this.title = title;
        this.priceStandart = priceStandart;
        this.priceLarge = priceLarge;
        this.diameterStandart = diameterStandart;
        this.diameterLarge = diameterLarge;
        this.description = description;
        this.imageSrc = imageSrc;
        this.imagePath = imagePath;
    }
}
