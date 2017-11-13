package com.iot.net;

import com.iot.store.MessageStore;
import com.iot.wrapper.MessageWrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@RestController
@RequestMapping("ai")
public class AIController {

    @RequestMapping(value="command",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    WebHookResponse command(@RequestBody String object) {
        JSONObject jsonObject = new JSONObject(object);

        System.out.println("JSON:"+jsonObject);

        JSONObject result =jsonObject.getJSONObject("result");
        JSONObject parameters = result.getJSONObject("parameters");
        JSONObject fulfillment = result.getJSONObject("fulfillment");

        String location = parameters.getString("location");
        String state = parameters.getString("state");
        String device = parameters.getString("device");
        String st =state.equalsIgnoreCase("off")?"1":"0";

        System.out.println(location+";"+state+";"+device);
        try {

            String sessionforLocation =Constants.roomDeviceMapping.get(location);
            String pinForDevice = Constants.objectPinMapping.get(device);

            System.out.println("sessionforLocation:"+sessionforLocation+"pinForDevice:"+pinForDevice);
            SocketHandler.deviceSession.get(sessionforLocation)
                    .sendMessage(new TextMessage("1;"
                            +pinForDevice+";"
                            +st));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String speech=fulfillment.getString("speech");
        return new WebHookResponse(speech,speech);

    }


    @RequestMapping(value="command_rasa",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    WebHookResponse commandRasa(@RequestBody String object) {
        JSONObject jsonObject = new JSONObject(object);

        System.out.println("JSON:"+jsonObject);

        String location = jsonObject.getString("location");
        String state = jsonObject.getString("state");
        String device = jsonObject.getString("device");
        String st =state.equalsIgnoreCase("off")?"1":"0";

        System.out.println(location+";"+state+";"+device);
        try {

            String sessionforLocation =Constants.roomDeviceMapping.get(location);
            String pinForDevice = Constants.objectPinMapping.get(device);

            System.out.println("sessionforLocation:"+sessionforLocation+"pinForDevice:"+pinForDevice);
            SocketHandler.deviceSession.get(sessionforLocation)
                    .sendMessage(new TextMessage("1;"
                            +pinForDevice+";"
                            +st));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String speech=fulfillment.getString("speech");
        return new WebHookResponse("done","done");

    }

    @RequestMapping(value="message_rasa",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    WebHookResponse messageRasa(@RequestBody String object) {
        JSONObject jsonObject = new JSONObject(object);

        System.out.println("JSON:"+jsonObject);

        String speech = jsonObject.getString("speech");
        String recipient = jsonObject.getString("recipient");

        System.out.println(speech+";"+recipient);
        try {
            MessageStore.getQueue().put(new MessageWrapper(recipient,speech,"FROMBOT"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new WebHookResponse("done","done");

    }



}
