package com.danny.smartcampus.Resources;

// imports java and other systems to make API functional
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;

// import models used for sensor system
import com.danny.smartcampus.Room;
import com.danny.smartcampus.Sensor;
import com.danny.smartcampus.SensorReading;

// handles requests for /api/v1/sensors
@Path("/api/v1/sensors")
public class SensorResource {

    // temporary db for readings linked to each sensor
    private static Map<String, List<SensorReading>> readings = new HashMap<>();

    // temporary db for all sensors
    private static Map<String, Sensor> sensors = new HashMap<>();

    // returns all sensors from hashmap with filtering using type
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensors(@QueryParam("type") String type) {

        // gets all sensors, only values needed not ids
        Collection<Sensor> allSensors = sensors.values();

        // checks if a type filter was added in URL
        if (type != null && !type.trim().isEmpty()) {

            // stores only matching sensors
            List<Sensor> filteredSensors = new ArrayList<>();

            // loops through the sensors and checks the type
            for (Sensor sensor : allSensors) {
                if (sensor.getType() != null &&
                        sensor.getType().equalsIgnoreCase(type.trim())) {
                    filteredSensors.add(sensor);
                }
            }

            // returns ok http
            return Response.ok(filteredSensors).build();
        }

        // if no filter used returns all sensors
        return Response.ok(allSensors).build();
    }

    // creates a new sensor in hashmap
    @POST

    // receives as json only
    @Consumes(MediaType.APPLICATION_JSON)

    // format and returns results as json
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {

        // checks room exists first before sensor is created
        Room room = RoomResource.getRoomByIdStatic(sensor.getRoomId());

        // if room does not exist returns not found
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room does not exist")
                    .build();
        }

        // if no id sent by client it generates one
        if (sensor.getId() == null || sensor.getId().isEmpty()) {
            sensor.setId(UUID.randomUUID().toString());
        }

        // save sensor into hashmap
        sensors.put(sensor.getId(), sensor);

        // links sensor to room by adding sensor id
        room.getSensorIds().add(sensor.getId());

        // returns created HTTP response
        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    // adds a reading to a specific sensor
    @POST
    @Path("/{id}/readings")

    // receives as json only
    @Consumes(MediaType.APPLICATION_JSON)

    // format and returns results as json
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(@PathParam("id") String sensorId, SensorReading reading) {

        // finds sensor using sensor id from url
        Sensor sensor = sensors.get(sensorId);

        // if sensor does not exist returns not found
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor was not able to be found")
                    .build();
        }

        // checks if sensor is under maintenance if yes it blocks new readings
        if (sensor.getStatus() != null &&
                sensor.getStatus().equalsIgnoreCase("MAINTENANCE")) {

            // returns HTTP 403 Forbidden
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Sensor is under maintenance")
                    .build();
        }

        // if no reading list exists yet it creates one
        readings.putIfAbsent(sensorId, new ArrayList<>());

        // links reading to correct sensor
        reading.setSensorId(sensorId);

        // if no timestamp sent it uses current system time
        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }

        // if no reading id sent it generates one
        if (reading.getId() == null || reading.getId().isEmpty()) {
            reading.setId(UUID.randomUUID().toString());
        }

        // saves reading inside reading history
        readings.get(sensorId).add(reading);

        // updates current value of parent sensor
        sensor.setCurrentValue(reading.getValue());

        // returns HTTP 201 Created
        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }

    // returns all readings for a specific sensor
    @GET
    @Path("/{id}/readings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings(@PathParam("id") String sensorId) {

        // checks sensor exists first
        if (!sensors.containsKey(sensorId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        // returns readings if found, if none exist returns empty list instead of null
        return Response.ok(
                readings.getOrDefault(sensorId, new ArrayList<>())).build();
    }

    // helper method if other classes need all sensors
    public static Collection<Sensor> getAllSensors() {
        return sensors.values();
    }
}