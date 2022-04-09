package analytics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.UtilsConstant;

public class Analyzor {
    String logPath = UtilsConstant.LOG_ROOT_FOLDER;

    public void reportQuery(){

    }
    public int countNumberOfQuery(String user,String database) throws IOException, ParseException{
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(logPath + "/queryLogs.json"));
        JSONObject logsJsonObject = (JSONObject) obj;
        JSONArray queryArray = (JSONArray) logsJsonObject.get(user);
        return queryArray.size();
        
    }
    public void countNumberOfupdate() throws FileNotFoundException, IOException, ParseException{
 JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(logPath + "/queryLogs.json"));
        JSONObject logsJsonObject = (JSONObject) obj;
        // JSONArray queryArray = (JSONArray) logsJsonObject.get(user);
    }
}
