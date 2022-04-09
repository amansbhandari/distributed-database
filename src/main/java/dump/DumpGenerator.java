package dump;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import utils.UtilsConstant;

public class DumpGenerator {
    // public  void main(String[] args) throws IOException {
    //     createStructureDump("DatabaseSample");
    //     createDataDump("databaseSample");
    // }
    String databasePath = UtilsConstant.DATABASE_ROOT_FOLDER;

    public  void createDataDump(String databaseName) throws IOException {
        
        List<File> files = getAllTablesUnderTheDirectoryFiles(databasePath+"/"+databaseName);
        String path = databasePath+"/"+databaseName+"/"+"GlobleMetaData.txt";
        File[] tables = new File[files.size()];
        tables = files.toArray(tables);
        tables = sortExcuteSequence(tables, path);
        FileWriter myWriter = new FileWriter("dumpData.txt");
        for (File file : tables) {
            
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            String tableName = file.getName().substring(0, file.getName().length() - 4);
            tableName = tableName.split("_")[1];
            // String localMetaDataPath = "localMetaData";
            String cols[] = getColumnsMethod(tableName, databaseName);
            while ((line = br.readLine()) != null) {
                String out = "INSERT INTO " + tableName + " ( ";
                String[] inputs = line.split("\\|", -1);
                int length = inputs.length;
                for (int i = 0; i < length - 1; i++) {
                    out = out + " " + cols[i] + ", ";
                }
                out = out + cols[length - 1];
                out = out + " )";
                out = out + "VALUES ( ";
                for (int i = 0; i < length - 1; i++) {
                    out = out + " " + inputs[i] + ", ";
                }
                out = out + inputs[length - 1] + " )";

                myWriter.append(out + "\n");
            }
            br.close();

        }
        myWriter.close();
    }
    public String[] getColumnsMethod(String tableName,String database) throws IOException{
        String path = databasePath+"/"+database+"/"+"metadata_"+tableName+".txt";
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        ArrayList<String> columns = new ArrayList<>();
        String line = br.readLine();
        while(line!=null){
            String col = line.split("[|]")[0];
            columns.add(col);
            line  = br.readLine();
        }
        String[] results = new String[columns.size()];
        return columns.toArray(results);

    }

    public  String[] getColumns(String tableName, String localMetaDataPath) throws IOException {
        List<File> files = getAllTablesUnderTheDirectoryFiles(localMetaDataPath);
        ArrayList<String> columns = new ArrayList<>();
        for (File file : files) {
            String fileName = file.getName().substring(0, file.getName().length() - 4).split("_")[1];
            BufferedReader br = new BufferedReader(new FileReader(file));
            if (fileName.equals(tableName)) {
                String col = br.readLine();
                while(col !=null){
                    col = col.split("\\|")[0];
                    columns.add(col);
                    col = br.readLine();
                }
                
            }
            br.close();
        }
        String[] results = new String[columns.size()];
        return columns.toArray(results);

    }

    public  String createScriputInsert(String line, String out, BufferedReader br) throws IOException {

        while ((line = br.readLine()) != null) {
            String[] inputs = line.split("\\|");
            int length = inputs.length;
            for (int i = 0; i < length - 1; i++) {
                out = out + " " + inputs[i] + ", ";
            }
            out = out + inputs[length - 1];
            out = out + " );";
            out = "Insert Into " + "T1" + " ( ";
        }

        br.close();
        return out;
    }

    public  void createStructureDump(String databaseName) throws IOException {

        List<File> files = getAllLocalMetaUnderTheDirectoryFiles(databasePath+"/"+databaseName);
        String path = databasePath+"/"+databaseName+"/"+"GlobleMetaData.txt";
        File[] tables = new File[files.size()];
        tables = files.toArray(tables);
        tables = sortExcuteSequence(tables, path);
        FileWriter myWriter = new FileWriter("dump.txt");
        for (File file : tables) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            String tableName = file.getName().substring(0, file.getName().length() - 4);
            tableName = tableName.split("_")[1];
            String out = "CREATE TABLE " + tableName + " ( ";
            out = createScriput(line, out, br);
            myWriter.append(out + "\n");
        }
        myWriter.close();

    }

    private  File[] sortExcuteSequence(File[] tables, String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = null;
        HashMap<String, Integer> orderTableMap = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] inputs = line.split("\\|");

            orderTableMap.put(inputs[0], Integer.parseInt(inputs[1]));
        }
        for (int i = 0; i < tables.length; i++) {

            for (int j = i + 1; j < tables.length; j++) {
                String nameI = tables[i].getName().substring(0, tables[i].getName().length() - 4);
                nameI = nameI.split("_")[1];
                String nameJ = tables[j].getName().substring(0, tables[j].getName().length() - 4);
                nameJ = nameJ.split("_")[1];
                if (orderTableMap.get(nameI) > orderTableMap.get(nameJ)) {
                    File temp = tables[i];
                    tables[i] = tables[j];
                    tables[j] = temp;
                }
            }
        }
        br.close();
        return tables;
    }

    public  String createScriput(String line, String out, BufferedReader br) throws IOException {
        String primaryKey = "";
        String forigenkey = "";
        while ((line = br.readLine()) != null) {

            String[] inputs = line.split("[|]", -1);
            out = out + " " + inputs[0] + " " + inputs[1] + " ";
            if (inputs[2].equals("true")) {
                out = out + "NOT NULL, ";
            }
            if (!inputs[3].equals("")) {
                primaryKey = "PRIMARY KEY (" + inputs[0] + ")";
            }
            if (!inputs[4].equals("")) {
                forigenkey = "FOREIGN KEY (" + inputs[6] + ") REFERENCES " + inputs[5] + "(" + inputs[6] + ")";
            }

        }

        if (!primaryKey.equals("")) {
            out = out + ", " + primaryKey;
        }
        if (!forigenkey.equals("")) {
            out = out + ", " + forigenkey;
        }
        out = out + " );";

        br.close();
        return out;

    }

    public void createDataDump() {

    }

    public  List<File> getAllTablesUnderTheDirectoryFiles(String path) {
        try {
            List<File> filesInFolder = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .filter(file->file.getFileName().toString().startsWith("table"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            return filesInFolder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    
    public List<File> getAllLocalMetaUnderTheDirectoryFiles(String path) {
        try {
            List<File> filesInFolder = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().startsWith("metadata"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            return filesInFolder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}