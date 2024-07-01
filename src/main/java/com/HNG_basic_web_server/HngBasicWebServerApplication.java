package com.HNG_basic_web_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.HNG_basic_web_server.proxy"})
public class HngBasicWebServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HngBasicWebServerApplication.class, args);
	}

}
