package com.HNG_basic_web_server.services;

import com.HNG_basic_web_server.DTO.LocationInfoDTO;
import com.HNG_basic_web_server.DTO.ResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;




@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;
//    Logger logger = Logger.getLogger("UserService.class");

    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }


    public LocationInfoDTO getLocationInfo(HttpServletRequest request) {
        LocationInfoDTO e = null;
        try {
            String uri = "http://api.weatherapi.com/v1/current.json?key=9b4ac5bcbabb4632a2f194533240307&q=" + "129.205.113.173";

            e = restTemplate.getForObject(uri, LocationInfoDTO.class);
        } catch (RuntimeException r) {
//            logger.info("error: " + r);
        }
        return e;
    }

//    public LocationInfoDTO getLocationInfo(HttpServletRequest request) {
//        LocationInfoDTO e = null;
//        try {
//            String uri = "http://api.weatherapi.com/v1/current.json?key=9b4ac5bcbabb4632a2f194533240307&q=" + getIpaddress(request);
//
//            String rawResponse = restTemplate.getForObject(uri, String.class);
//            System.out.println("Raw Response: " + rawResponse);
//
//            ObjectMapper mapper = new ObjectMapper();
//            e = mapper.readValue(rawResponse, LocationInfoDTO.class);
//        } catch (RuntimeException r) {
//            logger.info("error: " + r);
//        } catch (IOException ioException) {
//            logger.info("error: " + ioException);
//        }
//        return e;
//    }


    public ResponseDTO mapInfo(LocationInfoDTO locationInfo, HttpServletRequest request) {

        //        logger.info("location info details: " + locationInfo);
        locationInfo = getLocationInfo(request);

        String location = locationInfo.getLocation().getRegion();
        double temperature = locationInfo.getCurrent().getTemp_c();


        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setLocation(location);


        return responseDTO;
    }

    public String checkName(HttpServletRequest request) {

        String name = request.getParameter("visitor_name");
        if (name.startsWith("\"")) {
          name =  name.substring(1, name.length()-1);
        }
        return name;
    }
}
