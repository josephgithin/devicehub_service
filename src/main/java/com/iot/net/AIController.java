package com.iot.net;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@RestController
@RequestMapping("ai")
public class AIController {

    @RequestMapping(path="command",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    WebHookResponse command(@RequestBody String object) {
        JSONObject jsonObject = new JSONObject(object);
        JSONObject result =jsonObject.getJSONObject("result");
        JSONObject parameters = result.getJSONObject("parameters");
        JSONObject fulfillment = result.getJSONObject("fulfillment");

        String location = parameters.getString("location");
        String state = parameters.getString("state");
        String device = parameters.getString("device");
        String st =state.equalsIgnoreCase("off")?"1":"0";
        try {
            SocketHandler.deviceSession.get(Constants.roomDeviceMapping.get(location))
                    .sendMessage(new TextMessage("1;"
                            +Constants.objectPinMapping.get(device)+";"
                            +st));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String speech=fulfillment.getString("speech");
        return new WebHookResponse(speech,speech);

    }

}
