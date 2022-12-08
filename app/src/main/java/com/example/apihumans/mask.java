package com.example.apihumans;

import android.os.Parcel;
import android.os.Parcelable;

public class mask implements Parcelable {

    private int Id;
    private String Products;
    private int Amount;
    private String Image;

    protected mask(Parcel in){
        Id = in.readInt();
        Products = in.readString();
        Amount = in.readInt();
        Image = in.readString();
    }

    public mask(int ID, String Products, int Amount,String Image ) {
        this.Id = ID;
        this.Products = Products;
        this.Amount = Amount;
        this.Image = Image;
    }

    public static final Creator<mask> CREATOR = new Creator<mask>() {
        @Override
        public mask createFromParcel(Parcel in) {
            return new mask(in);
        }

        @Override
        public mask[] newArray(int size) {
            return new mask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Products);
        dest.writeInt(Amount);
        dest.writeString(Image);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getProducts() {
        return Products;
    }

    public void setProducts(String products) {
        Products = products;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
