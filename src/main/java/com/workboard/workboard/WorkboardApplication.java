package com.workboard.workboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WorkboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkboardApplication.class, args);
    }

}
