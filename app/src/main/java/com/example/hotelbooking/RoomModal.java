package com.example.hotelbooking;

public class RoomModal {
    private int id;
    private String roomName;
    private String squareFeet;
    private String beds;
    private String people;
    private String price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(String squareFeet) {
        this.squareFeet = squareFeet;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    // constructor
    public RoomModal(int id, String roomName, String squareFeet, String beds, String people, String price) {
        this.id = id;
        this.roomName = roomName;
        this.squareFeet = squareFeet;
        this.beds = beds;
        this.people = people;
        this.price = price;
    }
}
