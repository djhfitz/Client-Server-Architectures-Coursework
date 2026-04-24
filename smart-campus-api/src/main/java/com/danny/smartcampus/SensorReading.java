package com.danny.smartcampus;

// imports java and other systems to make API functional

public class SensorReading {

    // unique ID for each reading
    private String id;

    // stores the time the reading was created
    private long timestamp;

    // stores the actual reading value from the sensor
    private double value;

    // links the reading back to the correct sensor
    private String sensorId;

    // empty constructor needed so JSON can create SensorReading objects
    // automatically
    public SensorReading() {
    }

    // constructor used when creating a reading with values already set
    public SensorReading(String id, long timestamp, double value, String sensorId) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
        this.sensorId = sensorId;
    }

    // gets reading ID
    public String getId() {
        return id;
    }

    // sets reading ID
    public void setId(String id) {
        this.id = id;
    }

    // gets reading timestamp
    public long getTimestamp() {
        return timestamp;
    }

    // sets reading timestamp
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // gets sensor reading value
    public double getValue() {
        return value;
    }

    // sets sensor reading value
    public void setValue(double value) {
        this.value = value;
    }

    // gets linked sensor ID
    public String getSensorId() {
        return sensorId;
    }

    // sets linked sensor ID
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}