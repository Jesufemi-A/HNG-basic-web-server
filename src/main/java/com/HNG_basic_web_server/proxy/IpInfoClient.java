package com.HNG_basic_web_server.proxy;

import com.HNG_basic_web_server.projectconfig.ProjectConfig;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "clientInfo", url = "http://api.weatherapi.com/v1")
public interface IpInfoClient {

    @GetMapping("/ip.json")
    @Headers(value="Content-Type: application/json")
    public Map<String, Object> getLocation(@RequestParam("key") String apiKey, @RequestParam("q") String ip);

    @GetMapping("/current.json")
    @Headers(value="Content-Type: application/json")
   public Map<String, Object> getCurrentWeather(@RequestParam("key") String apiKey
            , @RequestParam("q") String ip);
}


