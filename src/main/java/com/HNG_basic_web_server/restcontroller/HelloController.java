package com.HNG_basic_web_server.restcontroller;


import com.HNG_basic_web_server.DTO.LocationInfoDTO;
import com.HNG_basic_web_server.DTO.ResponseDTO;
import com.HNG_basic_web_server.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class HelloController {

    Logger logger = Logger.getLogger("HelloController.class");

    private final UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<ResponseDTO> doHello(@RequestParam(required = false) String visitor_name,
                                             HttpServletRequest request) {

        String ipAddress = userService.getIpAddress(request);
        logger.info("ipAddress: " + ipAddress );
        LocationInfoDTO locationInfo = userService.getLocationInfo(request);
        logger.info("locationInfo: " + locationInfo );
        String location = locationInfo.getLocation().getRegion();
        logger.info("location: " + location);
        double temperature = locationInfo.getCurrent().getTemp_c();
        logger.info("temperature: " + temperature);

        String greeting = "Hello, " + userService.checkName(request) + "!, the temperature is "
                + temperature + " degrees Celcius in " + location;


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setLocation(location);
        responseDTO.setClient_ip(ipAddress);
        responseDTO.setGreeting(greeting);

        return ResponseEntity.status(200).body(responseDTO);
    }
}
