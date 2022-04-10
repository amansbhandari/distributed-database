package utils;

import DiskHandler.DistributedManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class UtilsMetadata {

    public static Boolean fkRefExists(String tableMetadataFilePath , String columnName)
    {
        File f = new File(tableMetadataFilePath);
        if(f.exists() && !f.isDirectory()) {    //table exists
            try {
                Scanner in = new Scanner(new FileReader(tableMetadataFilePath));
                while(in.hasNext()) {
                    String line = in.next();
                    if(line.indexOf(columnName+"|") != -1)
                    {
                       return true;     //found columnname in metadata
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return false;

        }

        return false;
    }

    public static List getColumnListAtIndex(String filePath, int colNo)
    {
        List results = new ArrayList();
        try {
            Scanner in = new Scanner(new FileReader(filePath));
            while(in.hasNext()) {
                String line = in.next();
                String elements[] = line.split("[|]");

                results.add(elements[colNo]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static int indexOfPrimarykey(String filePath)
    {
        int index = 0;

        try {
            Scanner in = new Scanner(new FileReader(filePath));
            while(in.hasNext()) {
                String line = in.next();
                String elements[] = line.split("[|]");

                if(elements.length > 3 &&  elements[3].equals("PK"))
                {
                    return index;
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public static String getPrimarykey(String filePath)
    {
        int index = 0;

        try {
            Scanner in = new Scanner(new FileReader(filePath));
            while(in.hasNext()) {
                String line = in.next();
                String elements[] = line.split("[|]");

                if(elements.length > 3 &&  elements[3].equals("PK"))
                {
                    return elements[0];
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getForeignkey(String filePath)
    {
        int index = 0;

        try {
            Scanner in = new Scanner(new FileReader(filePath));
            while(in.hasNext()) {
                String line = in.next();
                String elements[] = line.split("[|]");

                if(elements.length > 4 && elements[4].equals("FK"))
                {
                    return elements[0];
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getForeignKeyReference(String filePath)
    {
        int index = 0;

        try {
            Scanner in = new Scanner(new FileReader(filePath));
            while(in.hasNext()) {
                String line = in.next();
                String elements[] = line.split("[|]");

                if(elements.length > 4 && elements[4].equals("FK"))
                {
                    return elements[5] + "." + elements[6];
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Boolean primaryKeyViolation(String metaFilePath, String tableFilePath, String valueToCheck)
    {
        int index = indexOfPrimarykey(metaFilePath);

        List<String> colValues = getColumnListAtIndex(tableFilePath,index);

        for(String element : colValues)
        {
            if(valueToCheck.equals(element))
            {
                return true;
            }
        }

        return false;
    }


    public static int getIndexOfColumnInTable(String filePath, String colname)
    {
        int index = 0;

        try {
            Scanner in = new Scanner(new FileReader(filePath));
            while(in.hasNext()) {
                String line = in.next();
                String elements[] = line.split("[|]");

                if(elements[0].equals(colname))
                {
                    return index;
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static Boolean foreignKeyViolation(String tableFilePath, String valueToCheck, int index)
    {
        List<String> colValues = getColumnListAtIndex(tableFilePath,index);
        Boolean flag = true;

        for(String element : colValues)
        {
            if(valueToCheck.equals(element))
            {
                flag = false;
            }
        }

        return flag;
    }

    public static HashMap<String, List<String>> fetchDBData(String databaseName, String filePrefix) throws IOException {
        HashMap<String, List<String>> databaseMetadata = new HashMap<>();
        try {
            File databaseFolder = new File(UtilsConstant.DATABASE_ROOT_FOLDER+"/" + databaseName+"/global_metadata.txt");
            String readLine = "";
            String tableName = "";
            if (databaseFolder.isFile()) {
                Scanner readFile = new Scanner(databaseFolder);
                while (readFile.hasNext()) {
                    readLine = readFile.nextLine();
                    if (readLine.startsWith(filePrefix)) {
                        tableName = readLine.split("_")[1].split("\\|")[0];
                        List<String> tableMetadata = DistributedManager.readFile(databaseName, UtilsConstant.DATABASE_ROOT_FOLDER+"/" + databaseName + "/" + readLine.split("\\|")[0], readLine.split("\\|")[0]);
                        databaseMetadata.put(tableName.substring(0, tableName.length() - 4), tableMetadata);
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return databaseMetadata;
    }

}
