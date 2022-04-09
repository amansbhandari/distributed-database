package reverseEngineering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class DrawERD {
    private String mDatabaseFilePath;
    private String erd = "";

    public String draw(String[] rankOrder, String databaseName, HashMap<String, HashMap<String, String[]>> dependencyGraph) {
        erd = "";
        mDatabaseFilePath = System.getProperty("user.dir") + "\\database\\" + databaseName + "\\";
        for (int i = 0; i < rankOrder.length; i++) {
            if (rankOrder[i] == null)
                continue;
            erd += "-".repeat(35) + "\n";
            erd += "| " + rankOrder[i].toUpperCase() + " ".repeat(31 - rankOrder[i].length()) + " |\n";
            erd += "-".repeat(35) + "\n";
            readMetadata(rankOrder[i]);
            erd += "\n";
            if (i < rankOrder.length - 1 && rankOrder[i + 1] != null) {
                String[] relationship = dependencyGraph.get(rankOrder[i + 1]).get(rankOrder[i]);
                if (relationship != null) {
                    erd += (" ".repeat(17) + "|\n").repeat(2);
                    erd += " ".repeat(16) + relationship[2] + "\n";
                    erd += (" ".repeat(17) + "|\n").repeat(2);
                } else {
                    erd += "\n";
                }
            }
        }
        return erd;
    }

    private void readMetadata(String tableName) {
        File tableFile = new File(mDatabaseFilePath + "metadata_" + tableName+".txt");
        String[] columnDesc;
        if (tableFile.isFile()) {
            try {
                Scanner readFile = new Scanner(tableFile);
                while (readFile.hasNext()) {
                    columnDesc = readFile.nextLine().split("[|]");
                    erd += "|  " + columnDesc[0] + " " + columnDesc[1] + " ".repeat(30 - (columnDesc[0] + columnDesc[1]).length()) + "|\n";
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        erd += "-".repeat(35);
    }
}
