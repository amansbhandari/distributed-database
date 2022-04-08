package utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class UtilsFileHandler {

    public static void writeToFile(String filepath, String content) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filepath, "UTF-8");
        writer.println(content);
        writer.close();
    }
}
