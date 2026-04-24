package com.danny.smartcampus.Resources;

// imports java and other systems to make API functional
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;

// import model from room
import com.danny.smartcampus.Room;

// handles requests for /api/v1/rooms
@Path("/api/v1/rooms")
public class RoomResource {

    // temporary db
    private static Map<String, Room> roomStorage = new HashMap<>();

    // returns all rooms in hashmap
    @GET

    // format and returns results as json
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRooms() {

        // we only need rooms not their ids so its within a collection
        Collection<Room> storedRooms = roomStorage.values();

        // shorthand status code for ok, then sends registered rooms back to client
        return Response.ok(storedRooms).build();
    }

    // create new room for hashmap
    @POST

    // receives as json only
    @Consumes(MediaType.APPLICATION_JSON)
    // format and returns results as json
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {

        // if no id send by client it will generate one
        if (room.getId() == null || room.getId().isEmpty()) {
            room.setId(UUID.randomUUID().toString());
        }

        // save room in hashmap
        roomStorage.put(room.getId(), room);

        // return HTTP status
        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    // calls for a room by its id
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomById(@PathParam("id") String id) {

        // takes the value and searches
        Room existingRoom = roomStorage.get(id);

        // returns http saying it searched but not found
        if (existingRoom == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room could not be found.")
                    .build();
        }

        // returns http confirming it found it
        return Response.ok(existingRoom).build();
    }

    // delete room based on id
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("id") String id) {

        // checks room exists first
        Room existingRoom = roomStorage.get(id);

        // if doesnt exist returns http saying not found
        if (existingRoom == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found, deletion task unnecessary.")
                    .build();
        }

        // checks if there are sensors on the room before deleting
        if (existingRoom.getSensorIds() != null &&
                !existingRoom.getSensorIds().isEmpty()) {

            // if there are sensors doesnt allow and returns http conflict
            return Response.status(Response.Status.CONFLICT)
                    .entity("Cannot delete room as there are still sensors assigned to it, please delete sensors and try again.")
                    .build();
        }

        // if safe deletes room
        roomStorage.remove(id);

        // returns http confirming it
        return Response.noContent().build();
    }

    // helper method for sensor to see if a room exists.
    public static Room getRoomByIdStatic(String id) {
        return roomStorage.get(id);
    }
}