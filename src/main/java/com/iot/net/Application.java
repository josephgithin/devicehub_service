package com.iot.net;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {


    public static void main(String[] args) {

        Constants.objectPinMapping.put("lights","12");
        Constants.objectPinMapping.put("light","12");
        Constants.objectPinMapping.put("television","13");
        Constants.objectPinMapping.put("fan","14");
        Constants.objectPinMapping.put("music","15");
        Constants.roomDeviceMapping.put("living","DEV2");
        Constants.roomDeviceMapping.put("bed","DEV1");

        SpringApplication.run(Application.class, args);

    }
}