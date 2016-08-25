package com.zeng.Tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;


/**
 * Created by zeng on 2016-07-19.
 */
public class JsonSplit {
    public static HashMap<String,String> splitJson(String s) {
        //HashMap<String,String> jsonList = new  HashMap<String,String>();
        ObjectMapper mapper1 = new ObjectMapper();
        HashMap<String,String> rtn = new HashMap<String,String>();
        HashMap<String,String> jobj = null;
        try {
            jobj = mapper1.readValue(s, HashMap.class);
            for (Map.Entry<String, String> entrySet : jobj.entrySet()) {
                String key = entrySet.getKey();
                Object value = entrySet.getValue();
                rtn.put(key, mapper1.writeValueAsString(value));
            }
        } catch (IOException ex) {
            Logger.getLogger(JsonSplit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rtn;
    }
}
