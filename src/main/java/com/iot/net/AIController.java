package com.iot.net;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ai")
public class AIController {

    @RequestMapping(path="command",method = {RequestMethod.POST,RequestMethod.GET})
    public String command(@RequestBody String object) {
        System.out.println(object);
        return new String("OK");

    }

}
