package com.example.hotelbooking;

public class RecentModal {

    String place;
    String country;
    String price;
    Integer imageURL;

    public Integer getImageURL() {
        return imageURL;
    }

    public void setImageURL(Integer imageURL) {
        this.imageURL = imageURL;
    }

    public RecentModal(String place, String country, String price, Integer imageURL) {
        this.place = place;
        this.country = country;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
