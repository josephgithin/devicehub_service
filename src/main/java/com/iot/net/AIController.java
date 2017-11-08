package com.iot.net;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ai")
public class AIController {

    @RequestMapping(method= RequestMethod.POST)
    public @ResponseBody
    void sayHello(String values) {
        System.out.println(values);
    }

}
