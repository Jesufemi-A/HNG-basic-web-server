//package com.HNG_basic_web_server.restcontroller;
//
//import com.HNG_basic_web_server.DTO.ResponseDTO;
//import com.HNG_basic_web_server.proxy.IpInfoClient;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Logger;
//
//@RestController
//@RequestMapping("/api")
//public class MyController {
//
//    @Autowired
//    private IpInfoClient ipInfoClient;
//
//    @Value("${weatherapi.key}")
//    private String apiKey;
//
//    public  Logger logger = Logger.getLogger("MyController.class");
//
//    @GetMapping("/hello")
//    public ResponseEntity<ResponseDTO> hello(@RequestParam String visitor_name, HttpServletRequest request) {
//        String clientIp = request.getHeader("X-Forwarded-For");
//        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
//            clientIp = request.getHeader("Proxy-Client-IP");
//        }
//        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
//            clientIp = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
//            clientIp = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
//            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
//            clientIp = request.getRemoteAddr();
//        }
//
//        logger.info("ip: " + clientIp);
//
//        // Check for local addresses and replace them with a placeholder public IP for testing
////        if (clientIp.equals("0:0:0:0:0:0:0:1") || clientIp.equals("127.0.0.1")) {
////            clientIp = "8.8.8.8";  // Google's public DNS IP address
////        }
//        String location = getLocation(clientIp);
//        String temperature = getTemperature(location);
//
////        Map<String, Object> response = new HashMap<>();
////        response.put("client_ip", clientIp);
////        response.put("location", location);
////        response.put("greeting", "Hello, " + visitor_name + "!, the temperature is " + temperature + " degrees Celsius in " + location);
//
//        return ResponseEntity.status(200).body(
//                new ResponseDTO(clientIp, location,
//                        "Hello, " + visitor_name + "!, the temperature is " + temperature + " degrees Celsius in " + location)
//        );
//    }
//
//    private String getClientIp(HttpServletRequest request) {
//        String clientIp = request.getRemoteAddr();
//
//        // Check for local addresses and replace them with a placeholder public IP
//        if (clientIp.equals("0:0:0:0:0:0:0:1") || clientIp.equals("127.0.0.1")) {
//            // Replace with a placeholder IP for testing
//            clientIp = "8.8.8.8";  // Google's public DNS IP address
//        }
//
//        return clientIp;
//    }
//
//    private String getLocation(String ip) {
//        Map<String, Object> response = ipInfoClient.getLocation(apiKey, ip);
//        return response.get("city").toString();
//    }
//
//    private String getTemperature(String location) {
//        Map<String, Object> response = ipInfoClient.getCurrentWeather(apiKey, location);
//        Map<String, Object> current = (Map<String, Object>) response.get("current");
//        return current.get("temp_c").toString();
//    }
//}
