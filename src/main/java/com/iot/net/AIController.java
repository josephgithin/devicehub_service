package com.iot.net;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ai")
public class AIController {

    @RequestMapping(path="command",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    WebHookResponse command(@RequestBody String object) {
        System.out.println(object);
        return new WebHookResponse("Operation Successfull","Operation Succesfull");

    }

}
