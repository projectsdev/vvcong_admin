package com.example.vyspsrivyavasayiadmin;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ObjectToJson {
    public JSONObject objectToJSONObject(Object object){		//FUNCTION TAKEN FROM STACKEXCHANGE
        Object json = null;
        JSONObject jsonObject = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONObject) {
            jsonObject = (JSONObject) json;
        }
        return jsonObject;
    }
}
