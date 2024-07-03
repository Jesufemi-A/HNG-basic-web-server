package com.HNG_basic_web_server.DTO;


import lombok.Data;

@Data
public class ResponseDTO {

    private String client_ip;
    private String location;
    private String greeting;
}
