package loger;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.UtilsConstant;

public class Loger {
    String logPath = UtilsConstant.LOG_ROOT_FOLDER;

    public void wirteLogs() {

    }

    public void writeEventLog(LogsParameters params) throws IOException, ParseException {
        FileWriter fileWriter;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(logPath + "/eventLogs.json"));
            JSONObject logsJsonObject = (JSONObject) obj;
            JSONArray eventLogArray = (JSONArray) logsJsonObject.get("events");
            JSONObject eventLog = new JSONObject();
            eventLog.put("timeStamp",params.timeStamp);
            eventLog.put("event", params.event);
            eventLog.put("onDatabase", params.database);
            eventLog.put("onTable", params.table);
            eventLog.put("condition", params.condition);
            eventLog.put("numberOfRowsChanges", params.numberOfRowsChanges);
            eventLog.put("columns", params.columns);
            eventLog.put("values", params.values);
            eventLog.put("crashReport", params.crashReport);
            eventLogArray.add(eventLog);
            fileWriter = new FileWriter(logPath + "/eventLogs.json");
            fileWriter.append(logsJsonObject.toJSONString());
        } catch (Exception e) {
            fileWriter = new FileWriter(logPath + "/eventLogs.json");
            String jsonString = "{\"events\":[]}";
            JSONObject logsJsonObject = (JSONObject) parser.parse(jsonString);
            fileWriter.append(logsJsonObject.toJSONString());
            JSONArray eventLogArray = (JSONArray) logsJsonObject.get("events");
            JSONObject eventLog = new JSONObject();
            eventLog.put("timeStamp", params.timeStamp);
            eventLog.put("event", params.event);
            eventLog.put("onDatabase", params.database);
            eventLog.put("onTable", params.table);
            eventLog.put("condition", params.condition);
            eventLog.put("numberOfRowsChanges", params.numberOfRowsChanges);
            eventLog.put("columns", params.columns);
            eventLog.put("values", params.values);
            eventLog.put("crashReport", params.crashReport);
            eventLogArray.add(eventLog);
            fileWriter = new FileWriter(logPath + "/eventLogs.json");
            fileWriter.append(logsJsonObject.toJSONString());
        }
        fileWriter.close();
    }

    public void writeDatabaseLog(LogsParameters params) throws IOException, ParseException {
        FileWriter fileWriter;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(logPath + "/databaseLogs.json"));
            JSONObject logsJsonObject = (JSONObject) obj;
            // String databaseName = "University";
            // int numberOfTables = 2;
            // String[] tableNames = { "students", "courses" };
            // int[] numberOfRows = { 4, 2 };
            JSONArray databaseLogArray = (JSONArray) logsJsonObject.get("databases");
            JSONObject databaseObject = new JSONObject();
            databaseObject.put("databaseName", params.database);
            databaseObject.put("numberOfTables", params.numberOfTables);
            JSONArray tableObjects = new JSONArray();
            for (int i = 0; i < params.numberOfTables;i++) {
                JSONObject table = new JSONObject();
                table.put("tableName", params.tableNames[i]);
                table.put("numberOfRows", params.numberOfRows[i]);
                tableObjects.add(table);
            }
            databaseObject.put("tables",tableObjects);
            databaseLogArray.add(databaseObject);
            fileWriter = new FileWriter(logPath + "/databaseLogs.json");
            fileWriter.append(logsJsonObject.toJSONString());
        } catch (Exception e) {
            fileWriter = new FileWriter(logPath + "/databaseLogs.json");
            String jsonString = "{\"databases\":[]}";
            JSONObject logsJsonObject = (JSONObject) parser.parse(jsonString);
            fileWriter.append(logsJsonObject.toJSONString());
            
            // String databaseName = "University";
            // int numberOfTables = 2;
            // String[] tableNames = { "students", "courses" };
            // int[] numberOfRows = { 4, 2 };
             JSONArray databaseLogArray = (JSONArray) logsJsonObject.get("databases");
            JSONObject databaseObject = new JSONObject();
            databaseObject.put("databaseName", params.database);
            databaseObject.put("numberOfTables", params.numberOfTables);
            JSONArray tableObjects = new JSONArray();
            for (int i = 0; i < params.numberOfTables;i++) {
                JSONObject table = new JSONObject();
                table.put("tableName", params.tableNames[i]);
                table.put("numberOfRows", params.numberOfRows[i]);
                tableObjects.add(table);
            }
            databaseObject.put("tables",tableObjects);
            databaseLogArray.add(databaseObject);
            fileWriter = new FileWriter(logPath + "/databaseLogs.json");
            fileWriter.append(logsJsonObject.toJSONString());
            
        }
        fileWriter.close();
       

    }

    public void writeQueryLog(LogsParameters params) throws IOException, ParseException {
        FileWriter fileWriter;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(logPath + "/queryLog.json"));
            JSONObject logsJsonObject = (JSONObject) obj;
            // String queryString = "CREATE TABLE T1";
            // long excutionTime = 10;
            // String user = "Qiwei";
            // String timeStamp = new Timestamp(System.currentTimeMillis()).toString();
            // String type = "CREATE";
            JSONArray queryLogArray = (JSONArray) logsJsonObject.get("data");
            JSONObject queryLog = new JSONObject();
            queryLog.put("user", params.user);
            queryLog.put("database", params.database);
            queryLog.put("table", params.table);
            queryLog.put("query", params.queryString);
            queryLog.put("type", params.type);
            queryLog.put("excutionTime", params.excutionTime);
            queryLog.put("timeStamp", params.timeStamp);
            queryLog.put("codition", params.condition);
            queryLog.put("columns", params.columns);
            queryLog.put("values", params.values);
            queryLogArray.add(queryLog);
            fileWriter = new FileWriter(logPath + "/queryLog.json");
            fileWriter.append(logsJsonObject.toJSONString());
        } catch (Exception e) {
            fileWriter = new FileWriter(logPath + "/queryLog.json");
            String jsonString = "{\"data\":[]}";
            JSONObject logsJsonObject = (JSONObject) parser.parse(jsonString);
            fileWriter.append(logsJsonObject.toJSONString());

            JSONArray queryLogArray = (JSONArray) logsJsonObject.get("data");
            JSONObject queryLog = new JSONObject();
            queryLog.put("user", params.user);
            queryLog.put("database", params.database);
            queryLog.put("table", params.table);
            queryLog.put("query", params.queryString);
            queryLog.put("type", params.type);
            queryLog.put("excutionTime", params.excutionTime);
            queryLog.put("timeStamp", params.timeStamp);
            queryLog.put("codition", params.condition);
            queryLog.put("columns", params.columns);
            queryLog.put("values", params.values);
            queryLogArray.add(queryLog);
            fileWriter = new FileWriter(logPath + "/queryLog.json");
            fileWriter.append(logsJsonObject.toJSONString());
        }
        fileWriter.close();

        // long excutionTime = getExcutionTime(query, sqlType);
        // String queryString = sqlType.toString()+" "+query.toString();
        // String user;
        // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // String type = sqlType.toString();
        // JSONObject queryLog= new JSONObject();
        // queryLog.put("query",queryString);

    }

    // public long getExcutionTime(Object query, SqlType sqlType) {
    // long startTime = System.nanoTime();
    // switch (sqlType) {
    // case CREATE:
    // CreateHandler.executeCreateQuery((CreateQuery) query);
    // break;
    // case SELECT:
    // SelectHandler.executeSelectQuery((SelectQuery) query);
    // break;
    // case INSERT:
    // InsertHandler.executeInsertQuery((InsertQuery) query);
    // break;

    // case DELETE:
    // DeleteHandler.executeDeleteQuery((DeleteQuery) query);
    // break;
    // // case UPDATE:
    // // UpdateHandler.executeUpdateQuery((UpdateQuery) query);
    // // break;
    // // case CHECK_SCHEMA:
    // // CheckSchemaHandler.executeCheckSchemaQuery((CheckSchemaQuery) query);
    // // break;
    // // case CREATE_SCHEMA:
    // // CreateSchemaHandler.executeCreateSchemaQuery((CreateSchemaQuery) query);
    // // break;
    // default:
    // break;
    // }

    // long stopTime = System.nanoTime();
    // long excutionTime = stopTime - startTime;
    // return excutionTime;
    // }

}