package com.HNG_basic_web_server.projectconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.HNG_basic_web_server.restcontroller"})
public class ProjectConfig {
}
