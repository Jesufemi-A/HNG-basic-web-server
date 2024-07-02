package com.HNG_basic_web_server.restcontroller;

import jakarta.servlet.http.HttpServletRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.juli.logging.Log;
import org.apache.logging.log4j.core.LogEvent;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@RestController
public class HelloController {


    private Logger logger = Logger.getLogger("HelloController.class");

    @Value("${OPENWEATHERMAP_API_KEY}")
    private String weatherApiKey;


    @GetMapping("/cron")
    public void doCron() {
        System.out.println("Keep alive");
    }


    @GetMapping("/api/hello")
    public String getHello(@RequestParam("visitor_name") String visitorName, HttpServletRequest request) {
        try {
            // Get client's IP address
//            String clientIp = request.getRemoteAddr();


            String clientIp = request.getHeader("X-Forwarded-For");
            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getHeader("Proxy-Client-IP");
            }
            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getHeader("WL-Proxy-Client-IP");
            }
            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getHeader("HTTP_CLIENT_IP");
            }
            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
                clientIp = request.getRemoteAddr();
            }

            logger.info("ip: " + clientIp);

//            clientIp = "63.116. 61.253";


            // Get location information from ipinfo.io
            HttpResponse<JsonNode> ipInfoResponse = Unirest.get("https://ipinfo.io/" + clientIp + "/json")
                    .header("accept", "application/json")
                    .asJson();

            if (ipInfoResponse.getStatus() != 200) {
                return "{\"error\": \"Failed to get IP information\"}";
            }

            kong.unirest.json.JSONObject ipInfoJson = ipInfoResponse.getBody().getObject();
            String city = ipInfoJson.getString("city");
            String loc = ipInfoJson.getString("loc");
            String[] locParts = loc.split(",");
            double latitude = Double.parseDouble(locParts[0]);
            double longitude = Double.parseDouble(locParts[1]);

            // Get temperature information using a weather API (OpenWeatherMap)
//             weatherApiKey = "${OPENWEATHERMAP_API_KEY}"; // Replace with your OpenWeatherMap API key
            HttpResponse<JsonNode> weatherResponse = Unirest.get("http://api.openweathermap.org/data/2.5/weather")
                    .queryString("lat", latitude)
                    .queryString("lon", longitude)
                    .queryString("appid", weatherApiKey)
                    .queryString("units", "metric") // For temperature in Celsius
                    .asJson();

            if (weatherResponse.getStatus() != 200) {
                return "{\"error\": \"Failed to get weather information\"}";
            }

            kong.unirest.json.JSONObject weatherJson = weatherResponse.getBody().getObject();
            double temperature = weatherJson.getJSONObject("main").getDouble("temp");

            // Construct the response JSON
            JSONObject responseObject = new JSONObject();
            responseObject.put("client_ip", clientIp);
            responseObject.put("location", city);
            responseObject.put("greeting", "Hello, " + visitorName + "!, the temperature is " + temperature + " degrees Celsius in " + city);

            return responseObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"An error occurred\"}";
        }
    }
}
