# Smart Campus REST API

## Student Details

**Student Name:** Danny Fitzgerald
**Student ID:** w2058708
**Module:** Client-Server Architectures
**Coursework:** Smart Campus REST API

---

## Project Overview

This project is a RESTful API for a Smart Campus system developed using Java, Maven, Jersey (JAX-RS), and the Grizzly HTTP Server, I created it and worked on it through Visual Studio Code.

The system is designed to manage university rooms, sensors placed inside those rooms, and the readings produced by those sensors, this system would be used to manage students aswell conditons like temperature, more conditons could be added and assigned to rooms in the future.

API functionality:

* room creation, ability to manage it aswell
* assigning sensors to rooms
* ability to view sensor data later on
* rules preventing invalid operations

Protected operations system include:

* does not allow creation of sensor if the room its being created for does not exist
* cannot delete a room if a sensor is already / currently assigned to it

My API doesnt include a database so for this iteration it utilises temporary storage in memory.

The server is started using `Main.java`, which then launches the Grizzly HTTP Server locally on `localhost:8080` rather then using something like GlassFish in NetBeans, I thought this showed my own ability to understand how servers start and allowed me to get a better understanding of Grizzly, on the otherhand I do understand why Enterprises would utilise something like GlassFish as it streamlines without needing a creation of a Main or manually entering commands into powershell to start said server.


I followed the requested GET, POST and DELETE mentioend in the brief so functionality follows REST princicples, I also added the HTTP status codes rather then just a basic simpler response.

---

## Technologies Used

| Component       | Technology          |
| --------------- | ------------------- |
| Language        | Java 17             |
| Build Tool      | Maven               |
| REST Framework  | Jersey (JAX-RS)     |
| Server          | Grizzly HTTP Server |
| Storage         | HashMap + ArrayList |
| IDE             | VS Code             |
| Version Control | GitHub              |
| Format          | JSON                |

---

### Resource Separation

Separate resource classes were created for: I seperated the classes for resources into their own folder, this meant I had to adapt the packages a little but I believe the added structure was worth it.

* Room management
* Sensor management

This would allow maintance and improvement and expansion down the line to be easier to navigate and manage.

---

### Object-Oriented Structure

I did have to go back through and adapt my code to fit private fields as on my inicial run I only used public fields, this took more time then I thought it would as I would get errors as changing one thing would affect another.

My improved code now follows the brief more closely and has better functionality.

---

### Response Handling

`Response` objects were used instead of plain text returns which means that the API can return status that are more relevant and sepecific for devs / managers to understand.

* 201 Created
* 404 Not Found
* 403 Forbidden
* 409 Conflict

This means the API better follows conventions and therefore is more professional and easier to understand as its more specific / self explanitory.

---

## Business Rules Implemented

A series of rules were implemented to prevent accidental deletion or mismanagament of data:

* sensors require a room to exist for it to be able to be created
* rooms have to have all sensors removed before it can be deleted
* maiatence sensors are being worked on so a rule is that they cannot accept new readings
* missing time can be generated
* missing IDs can be generated
* sensor readings update based on the latest

---

## API Endpoints

| Method | Endpoint                         | Description            |
| ------ | -------------------------------- | ---------------------- |
| GET    | /api/v1/rooms                    | Return all rooms       |
| POST   | /api/v1/rooms                    | Create a room          |
| GET    | /api/v1/rooms/{id}               | Return one room        |
| DELETE | /api/v1/rooms/{id}               | Delete a room          |
| GET    | /api/v1/sensors                  | Return all sensors     |
| GET    | /api/v1/sensors?type=temperature | Filter sensors by type |
| POST   | /api/v1/sensors                  | Register a sensor      |
| POST   | /api/v1/sensors/{id}/readings    | Add a reading          |
| GET    | /api/v1/sensors/{id}/readings    | Return sensor readings |

---

## Example JSON Requests

### Create Room

**POST /api/v1/rooms**

```json
{
  "id": "132",
  "name": "RoomName",
  "capacity": 60
}
```
The ID is for the room, its own unique identifier, the name is a human name for the type of room it is, is it a Libary or Lecture Hall etc, the capacity is how many people it can hold

---

### Create Sensor

**POST /api/v1/sensors**

```json
{
  "id": "242",
  "type": "Temperature",
  "roomId": "132",
  "status": "ACTIVE",
  "currentValue": 22.5
}
```
The ID is the unique identifier for this specific sensor, the type allows us to describe what the value is holding, iq is it temperature, humidity, energy usage etc. This means more unique sensors can be added for each room to keep track of muiltple variables, the room id tells us which room the sensor is assigned to. The status tells us if its operational or not, this gives us insight into if we should use the current values / readings below or if they could be inaccurate.

---

### Add Sensor Reading

**POST /api/v1/sensors/S1/readings**

```json
{
  "id": "READ1",
  "value": 24.7
}
```
The id tells lets us know this unique reading and allows the system to keep track of what it was for and such.

If no timestamp is provided, one is automatically generated by the system.

---

## How to Build and Run the API

### Step 1 — Open the Project

Open the project folder in VS Code and confirm it contains all the operational files and correct content:

---

### Step 2 — Check Requirements

Install:

* Java 17
* Maven

Verify using:

```bash
java -version
mvn -version
```

---

### Step 3 — Build the Project

Run:

```bash
mvn clean install
```

This installs dependencies and compiles the project.

Expected result:

```text
BUILD SUCCESS
```

---

### Step 4 — Start the Server

Run:

```text
Main.java
```

or from terminal:

```bash
mvn exec:java
```

---

### Step 5 — Test the API

The API runs at:

```text
http://localhost:8080/api/v1/rooms
```

Testing can be done using:

* browser
* Postman
* curl
* terminal

---

## Testing

The API was tested using:

* terminal
* browser

This helped verify:

* endpoint responses
* validation rules
* correct status codes
* sensor-to-room relationships

---

## Report — Answers to Coursework Questions

---

### Part 1: Service Architecture & Setup

---

#### Task 1.1 — Project & Application Configuration

**Question:**

Explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.

**Answer:**

JAX-RS will create a new object for every request, so this means unless specified otherwise it will make a new one each time, this means if we stored the date inside the class then it would be wiped each time it was called and put a fresh base data value, for example if we stored the room data inside the class when we add a room it will work first time but then we try add another or add something else like a sensor then it will vanish

---

#### Task 1.2 — The “Discovery” Endpoint

**Question:**

Why is the provision of “Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?

**Answer:**

It allows navigation and understanding of the api easier as the user wont have to memorise and know every endpoint, the links would be provided by the server rather then having to find every end path yourself, this would help massively in scale, imagine you have muiltple layers it could quickly become difficult to keep track of all the different urls, although my project doesnt include the system I fully understand why its implemention and usage is so powerful.

---

### Part 2: Room Management

---

#### Task 2.1 — Room Resource Implementation

**Question:**

When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.

**Answer:**

It would use less network bandwidth as their is less information, the same goes for clientside processing, if they only want the id it would be less demanding, however if they was then to request the rest of the information for the room it would then be higher as they would have to of made two requests.

---

#### Task 2.2 — Room Deletion & Safety Logic

**Question:**

Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.

**Answer:**

DELETE operation is idempotent in my implementation because even if the client sends the same request muiltple times the final effect will still be the same, as the request would be carried out if they then send the same request nothing new would happen so therefore its idempotent.

---

### Part 3: Sensors & Filtering

---

#### Task 3.1 — Sensor Registration & Content Validation

**Question:**

We explicitly use the @Consumes(MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?

**Answer:**

It means it will only accept the data in that format (JSON), if you try to send something that isnt that it would return an error.

---

#### Task 3.2 — Sensor Filtering Using Query Parameters

**Question:**

You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?

**Answer:**

Its better because its included the same endpoint and acts as an optional filter, this means that it wont read as its own independent endpoint, its also better as it can include muiltple query param without it changing the endpoint which is more efficient.

---

### Part 4: Sub-Resources

---

#### Task 4.1 — Sub-Resource Locator Pattern

**Question:**

Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path in one massive controller class?

**Answer:**

Keeping resources in seperate classes helps because it allows finding relevant methods easier, this seperation allows expandabiltiy to be more managable as its all catagorised, if the API was larger having all the methods in one class would quickly become difficult to filter through, it also helps with functionality as it will only import necessary resources at any specific time for that specific class.

---

### Part 5: Advanced Error Handling, Exception Mapping & Logging

---

#### Task 5.2 — Dependency Validation (422 Unprocessable Entity)

**Question:**

Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?

**Answer:**

Because of what it tells us, the 404 is more generic and just tells us it was unreachable / not found. Ff its 422 it tells us a little more such as that its Unprocessable Content meaning something is wrong with it being able to be process said instructions.

---

#### Task 5.4 — The Global Safety Net (500)

**Question:**

From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?

**Answer:**

Exposing internal Java stack traces to external API consumers gives addtional information of how the system works internally, this could expose details that could help the attack understand possible vunerabilities. With more information an attack is easier as they can gather infromation such as class / method names and other details, it could also further expose confidential information.

---

#### Task 5.5 — API Request & Response Logging Filters

**Question:**

Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?

**Answer:**

Because it keeps it seperate in one location and makes the code cleaner and easier to maintain as each resource will only have to manage their own purpose, having it in one location also prevents duplication and it also future proofs it with it only having to be updated in that one location.

---

## Limitations

My API uses in-memory storage only as I did not implement a database system.

This means that data is not stored and is lost upon ending of server runtime.

My API also doesnt include a friendly front facing UI or differing roles with privilliges etc, all of which could be implemented in the future if necessary.

---

## Project Scope and Time Constraints

The original coursework brief included a wider range of advanced REST API features and extended written analysis sections.

In the coursework brief the scope is slightly larger, due to time constraint and my personal situation I did decide to cut it down and focus on the current areas.

I decided to focus on the areas as follows:

- Room management
- Sensor registration
- Sensor reading
- Resource validation rules
- Proper HTTP status codes
- RESTful endpoint structure
- Clean JSON responses
- Correct relationships between rooms, sensors, and readings

---

## Artificial Intelligence Use Declaration

In preparing this coursework, I used ChatGPT as a support tool for understanding JAX-RS concepts, RESTful API design, and debugging parts of my implementation. This included help with improving endpoint handling, validation checks, response status codes, exception handling logic, and reviewing parts of the code structure.

During and in preperation for this coursework I utilised ChatGPT for both revision as a support tool to understand and setup / install parts of software and to understand designing the API. I also utilised it parcially for debugging and helping with small segments of code to improve their functionality or to better implement my own ideas. I made sure that I was not relyant on it in such a way to hinder my understanding so if there was a line that was from code I would still comment it to assure my understanding.

Small segements was AI-assisted, either for clarity reasons or because I could not find a optimal solution in a timely manner through searching through university resources or the web alone. I ensured everything I utilised it for however was understood by me.

I confirm I did not use AI to submit coursework completed on my behalf and only utilised it as a small and last resort, I remain fully responsible for the complete coursework and all submitted content.

