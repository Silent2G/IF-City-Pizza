package com.yleaf.stas.if_citypizza.model;

public class ReservedPizza {
    private int id;
    private boolean diameterLarge;

    public ReservedPizza(int id, boolean diameterLarge) {
        this.id = id;
        this.diameterLarge = diameterLarge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDiameterLarge() {
        return diameterLarge;
    }

    public void setDiameterLarge(boolean diameterLarge) {
        this.diameterLarge = diameterLarge;
    }
}
