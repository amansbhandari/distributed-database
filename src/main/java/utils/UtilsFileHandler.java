package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UtilsFileHandler {

    public static void writeToFile(String filepath, String content) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filepath, "UTF-8");
        writer.println(content);
        writer.close();
    }

    public static List<String> readFile(String filepath) throws FileNotFoundException, UnsupportedEncodingException {
        List content = new ArrayList<>();
        Scanner in = new Scanner(new FileReader(filepath));
        while (in.hasNext()) {
            content.add(in.next());
        }

        return content;
    }
}
