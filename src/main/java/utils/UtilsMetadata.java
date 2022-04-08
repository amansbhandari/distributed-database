package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
}
