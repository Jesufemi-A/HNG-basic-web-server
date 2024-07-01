package com.HNG_basic_web_server.restcontroller;

import com.HNG_basic_web_server.proxy.IpInfoClient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private IpInfoClient ipInfoClient;

    @Value("${weatherapi.key}")
    private String apiKey;
    private String clientIp, location, temperature;

    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello(@RequestParam String visitor_name, HttpServletRequest request) {
        clientIp = request.getRemoteAddr();
        location = getLocation(clientIp);
        temperature = getTemperature(location);

        Map<String, Object> response = new HashMap<>();
        response.put("client_ip", clientIp);
        response.put("location", location);
        response.put("greeting", "Hello, " + visitor_name + "!, the temperature is " + temperature + " degrees Celsius in " + location);

        return ResponseEntity.ok(response);
    }

    private String getLocation(String ip) {
        Map<String, Object> response = ipInfoClient.getLocation(apiKey, ip);
        return response.get("city").toString();
    }

    private String getTemperature(String location) {
        Map<String, Object> response = ipInfoClient.getCurrentWeather(apiKey, location);
        Map<String, Object> current = (Map<String, Object>) response.get("current");
        return current.get("temp_c").toString();
    }
}
