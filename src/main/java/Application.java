import java.io.FileReader;
import java.io.IOException;
import java.security.Timestamp;
import java.util.Date;
import java.util.logging.LogManager;

import javax.swing.text.Utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dump.DumpGenerator;
import loger.Loger;
import loger.LogsParameters;
import query.container.SqlType;
import utils.UtilsConstant;

public class Application {
    public static void main(String[] args) throws IOException, ParseException {
        // Entry point
      
        String event = "CREATE";
        String excutionTime = "10ms";
        String queryString = "CREATE TABLE T1";
        String user = "qiwei";
        // String timeStamp = new Timestamp(new Date());
        String type = "CREATE";
        String condition = "";
        String columns = "";
        String values = "";
        String database = "University";
        String table = "T1";
        int numberOfRowsChanges = 0;
        int numberOfTables = 2;
        String[] tableNames = {"students","university"};
        int[] numberOfRows = {4,2};
        Boolean isSuccessful = true;
        String crashReport = "";
        Loger loger = new Loger();

     
        // LogsParameters params = new LogsParameters(event, excutionTime, queryString, user, timeStamp, type, condition, columns, values, database, table, numberOfRowsChanges, numberOfTables, tableNames, numberOfRows, isSuccessful,crashReport);
        // loger.writeEventLog(params);
       

    }
}
