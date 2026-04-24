package com.danny.smartcampus;

// imports java and other systems to make API functional
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.io.IOException;
import java.net.URI;

public class Main {

    // main url for API, all endpoints will start from here
    public static final String api_url = "http://localhost:8080/";

    // starts the server and loads resources
    public static HttpServer startupServer() {

        // prints where it is being hosted
        System.out.println("Smart Campus API is now hosted at: " + api_url + "api/v1/rooms");

        // where to find resource classes
        final ResourceConfig apiconfigaration = new ResourceConfig()
                .packages("com.danny.smartcampus");

        // creates and starts the server
        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create(api_url), apiconfigaration);
    }

    // main method runs first when project starts
    public static void main(String[] args) throws IOException {

        // starts the server
        HttpServer server = startupServer();

        // confirms server is running
        System.out.println("API is now running at http://localhost:8080/api/v1/rooms");

        // tells user how to stop the server
        System.out.println("Enter or press a key in terminal to stop the server.");

        // waits for user input so server stays running
        System.in.read();

        // stops server after key is pressed
        System.out.println("Server stopping...");
        server.shutdownNow();
        System.out.println("Server stopped.");
    }
}