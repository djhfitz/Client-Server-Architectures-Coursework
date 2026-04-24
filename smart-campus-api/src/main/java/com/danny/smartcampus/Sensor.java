package com.danny.smartcampus;

public class Sensor {

    // unique ID for the sensor
    private String id;

    // type of sensor, allows muiltpurpose
    private String type;

    // stores which room the sensor belongs to and links back to room ID
    private String roomId;

    // current state of sensor, is it active or maintance and needs to be worked on
    // / not trustworthy results
    private String status;

    // stores the latest recorded value from the sensor
    private double currentValue;

    // empty constructor needed so JSON can create Sensor objects automatically
    public Sensor() {
    }

    // constructor used when creating a sensor with values already set
    public Sensor(String id, String type, String roomId, String status, double currentValue) {
        this.id = id;
        this.type = type;
        this.roomId = roomId;
        this.status = status;
        this.currentValue = currentValue;
    }

    // gets sensor ID
    public String getId() {
        return id;
    }

    // sets sensor ID
    public void setId(String id) {
        this.id = id;
    }

    // gets sensor type
    public String getType() {
        return type;
    }

    // sets sensor type
    public void setType(String type) {
        this.type = type;
    }

    // gets room ID linked to sensor
    public String getRoomId() {
        return roomId;
    }

    // sets room ID linked to sensor
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    // gets current sensor status
    public String getStatus() {
        return status;
    }

    // sets current sensor status
    public void setStatus(String status) {
        this.status = status;
    }

    // gets latest sensor value
    public double getCurrentValue() {
        return currentValue;
    }

    // sets latest sensor value
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }
}