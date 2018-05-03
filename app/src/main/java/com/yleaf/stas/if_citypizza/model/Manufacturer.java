package com.yleaf.stas.if_citypizza.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Manufacturer implements Parcelable {

    private int id;
    private String address;
    private String email;
    private String logoSrc;
    private String kyivstar;
    private String vodafone;
    private String lifecell;
    private String ukrtelecom;

    public Manufacturer() {}

    public Manufacturer(Parcel parcel) {
        this.id = parcel.readInt();
        this.address = parcel.readString();
        this.email = parcel.readString();
        this.logoSrc = parcel.readString();
        this.kyivstar = parcel.readString();
        this.vodafone = parcel.readString();
        this.lifecell = parcel.readString();
        this.ukrtelecom = parcel.readString();
    }

    public Manufacturer(int id, String address, String email,
                        String logoSrc, String kyivstar,
                        String vodafone, String lifecell,
                        String ukrtelecom) {
        this.id = id;
        this.address = address;
        this.email = email;
        this.logoSrc = logoSrc;
        this.kyivstar = kyivstar;
        this.vodafone = vodafone;
        this.lifecell = lifecell;
        this.ukrtelecom = ukrtelecom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.address);
        dest.writeString(this.email);
        dest.writeString(this.logoSrc);
        dest.writeString(this.kyivstar);
        dest.writeString(this.vodafone);
        dest.writeString(this.lifecell);
        dest.writeString(this.ukrtelecom);
    }

    public static final Creator<Manufacturer> CREATOR = new Creator<Manufacturer>() {
        @Override
        public Manufacturer createFromParcel(Parcel source) {
            return new Manufacturer(source);
        }

        @Override
        public Manufacturer[] newArray(int size) {
            return new Manufacturer[size];
        }
    };

    public int getId() {
        return id;
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
