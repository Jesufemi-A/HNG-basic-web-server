package com.HNG_basic_web_server.DTO;

public class ResponseDTO {

    private String clientIp;
    private String location;
    private String greeting;

    // Default constructor
    public ResponseDTO() {
    }

    // Parameterized constructor
    public ResponseDTO(String clientIp, String location, String greeting) {
        this.clientIp = clientIp;
        this.location = location;
        this.greeting = greeting;
    }

    // Getters and setters
    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
