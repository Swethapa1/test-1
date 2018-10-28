package input;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonClass {
	
	public JSONObject JsonObjectConstructor(ArrayList<String> keys,
			ArrayList<String> values) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(int keyIndex = 0; keyIndex < keys.size(); keyIndex++)
		{
			String key = keys.get(keyIndex);
			String value = values.get(keyIndex);
			map.put(key, value);
		}
		
		return(new JSONObject(map));
	}

}
