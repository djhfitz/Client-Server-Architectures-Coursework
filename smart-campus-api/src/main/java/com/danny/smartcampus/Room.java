package com.danny.smartcampus;

// imports java and other systems to make API functional
import java.util.ArrayList;
import java.util.List;

public class Room {

    // unique ID for the room
    private String id;

    // room name for readablity
    private String name;

    // maximum number of people allowed in the room
    private int capacity;

    // stores all sensor IDs linked to this room
    private List<String> sensorIds = new ArrayList<>();

    // empty constructor needed so JSON can create Room objects automatically
    public Room() {
    }

    // constructor used when creating a room with values already set
    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    // gets room ID
    public String getId() {
        return id;
    }

    // sets room ID
    public void setId(String id) {
        this.id = id;
    }

    // gets room name
    public String getName() {
        return name;
    }

    // sets room name
    public void setName(String name) {
        this.name = name;
    }

    // gets room capacity
    public int getCapacity() {
        return capacity;
    }

    // sets room capacity
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // gets all sensor IDs linked to room
    public List<String> getSensorIds() {
        return sensorIds;
    }

    // sets the full sensor ID list
    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }
}