package com.example.apihumans;

public class DataModal {
    private String Image;
    private String Products;
    private int Amount;

    public String getImage() {return Image;}

    public void setImage(String image) {Image = image;}

    public String getProducts() {return Products;}

    public void setProducts(String products) {Products = products;}

    public int getAmount() {return Amount;}

    public void setAmount(int amount) {Amount = amount;}

    public DataModal(String Image, String Products, int Amount){

        this.Image=Image;
        this.Products=Products;
        this.Amount=Amount;
    }
}
