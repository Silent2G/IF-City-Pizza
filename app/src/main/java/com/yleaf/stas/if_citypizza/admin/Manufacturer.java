package com.yleaf.stas.if_citypizza.admin;

public class Manufacturer {

    private String address;
    private String email;
    private String logoSrc;
    private String kyivstar;
    private String vodafone;
    private String lifecell;
    private String ukrtelecom;

    public Manufacturer() {}

    public Manufacturer(String address, String email,
                        String logoSrc, String kyivstar,
                        String vodafone, String lifecell,
                        String ukrtelecom) {

        this.address = address;
        this.email = email;
        this.logoSrc = logoSrc;
        this.kyivstar = kyivstar;
        this.vodafone = vodafone;
        this.lifecell = lifecell;
        this.ukrtelecom = ukrtelecom;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getLogoSrc() {
        return logoSrc;
    }

    public String getKyivstar() {
        return kyivstar;
    }

    public String getVodafone() {
        return vodafone;
    }

    public String getLifecell() {
        return lifecell;
    }

    public String getUkrtelecom() {
        return ukrtelecom;
    }
}
