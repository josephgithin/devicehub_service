package com.iot.net;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/ai")
public class AIController {

    @RequestMapping(method= RequestMethod.POST)
    public void sayHello(@RequestBody String values) {
        System.out.println(values);
    }

}
