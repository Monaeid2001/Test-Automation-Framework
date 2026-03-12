package com.automationexercise.utils.dataReader;

import com.automationexercise.utils.logs.LogsManager;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {

    /*
    -static - dynamic
    1.static(best el best)
    1.1 Database snapshot (restore)
    2.dynamic
    2.1 Database query (setup) //insert into users values('toBeModified','toBeModified@gmail.com','123456');
    2.2 API call (setup) // Post /users { "username": "toBeModified", "email"toBeModified@gmail.com", "password":"123456"} // network request
    2.3 UI interaction (setup)//CRUD operations
     */

    //excel - csv -json -properties
    //excel and csv not recommended for test data management cuz of complexity and not suitable for versioning control
    //properties - key value pairs - (not suitable for complex data structures)--> not sure about DSA
    //json - suitable for complex data structures - easy to read and write - suitable for versioning control
    private final String TEST_DATA_PATH = "src/test/resources/test-data/";
    String jsonReader;
    String jsonFileName;
    public JsonReader(String jsonFileName){
        this.jsonFileName = jsonFileName;
        try{
            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(TEST_DATA_PATH + jsonFileName + ".json")) ;
            jsonReader = data.toJSONString();
        }catch (Exception e){
            LogsManager.error("Exception occurred while reading JSON file: " , jsonFileName , e.getMessage());
            jsonReader = "{}";//initialize to empty JSON object to avoid null pointer exceptions
        }
    }
    //valid.username
    public String getJsonData(String jsonPath) {
        try{
            return JsonPath.read(jsonReader, jsonPath);
    }
        catch (Exception e){
            LogsManager.error("Exception occurred while getting JSON data for path: " , jsonPath , e.getMessage());
            return "";
        }
    }
}
