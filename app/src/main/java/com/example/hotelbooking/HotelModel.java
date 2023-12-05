package com.example.hotelbooking;

public class HotelModel {

    int id_hotel;
    String hotelName;
    String address;
    String lat;
    String lon;
    String hotelPhoneNO;
    int rooms;


    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getHotelPhoneNO() {
        return hotelPhoneNO;
    }

    public void setHotelPhoneNO(String hotelPhoneNO) {
        this.hotelPhoneNO = hotelPhoneNO;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }





//    public Hotel(String hotelName, String address, String lat, String lon, String hotelPhoneNO, int rooms, int rating_id, int amenity_id) {
//        this.hotelName = hotelName;
//        this.address = address;
//        this.lat = lat;
//        this.lon = lon;
//        this.hotelPhoneNO = hotelPhoneNO;
//        this.rooms = rooms;
//        this.rating_id = rating_id;
//        this.amenity_id = amenity_id;
//    }
}
