package reverseEngineering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReverseEngineering {
    private final String mDatabaseFilePath = "database\\";
    HashMap<String, HashMap<String, String>> dependencyGraph = new HashMap<>();
    HashMap<String, Integer> tableRank = new HashMap<>();
    HashMap<Integer, Integer> relationships = new HashMap<>();
    int count = 0;


    public String[] getRankOrder(String databaseName){
        generateTableRank(databaseName);
        return createRelationships(databaseName);
    }

    private void generateTableRank(String databaseName) {
        System.out.println(mDatabaseFilePath + databaseName);
        File databaseFolder = new File(mDatabaseFilePath + databaseName);
        File[] tableMetadataFiles = databaseFolder.listFiles();
        String tableName;

        for (File tableFile : tableMetadataFiles) {
            if (tableFile.isFile()) {
                if (tableFile.getName().startsWith("metadata")) {
                    tableName = tableFile.getName().split("_")[1];
                    dependencyGraph.put(tableName, null);
                    tableRank.put(tableName, count++);
                }
            }
        }
    }

    public String[] createRelationships(String databaseName) {
        File databaseFolder = new File(mDatabaseFilePath + databaseName);
        File[] tableMetadataFiles = databaseFolder.listFiles();
        String tableName;

        for (File tableFile : tableMetadataFiles) {
            if (tableFile.isFile()) {
                if (tableFile.getName().startsWith("metadata")) {
                    tableName = tableFile.getName().split("_")[1];
                    dependencyGraph.put(tableName, null);
                    readTableMetadata(tableFile, tableName);
                }
            }
        }
        System.out.println(tableRank);
        System.out.println(relationships);
        int[][] ints = new int[relationships.size()][2];
        int i = 0;
        for (Integer keys : relationships.keySet()) {
            ints[i][0] = keys;
            ints[i][1] = relationships.get(keys);
            i++;
        }
        System.out.println(Arrays.deepToString(ints));
        TopographicalSort topographicalSort = new TopographicalSort();
        List<Integer> tablePriorityOrder = topographicalSort.canFinish(relationships.size() + 1, ints);
        String[] rankedTables = new String[tableRank.size()];
        int count = 0;
        for (int j = tablePriorityOrder.size() - 1; j >= 0; j--) {
            for (Map.Entry<String, Integer> tableRankEntry: tableRank.entrySet()) {
                if (Objects.equals(tablePriorityOrder.get(j), tableRankEntry.getValue())) {
                    rankedTables[count++] = tableRankEntry.getKey();
                }
            }
        }
        return rankedTables;
    }

    private void readTableMetadata(File tableFile, String tableName) {
        String[] columnDesc;
        int columnDescLength;
        HashMap<String, String> dependencyHashMap = new HashMap<>();
        try {
            Scanner readFile = new Scanner(tableFile);
            while (readFile.hasNext()) {
                columnDesc = readFile.nextLine().split("[|]");
                columnDescLength = columnDesc.length;
                if (columnDescLength > 4) {
                    dependencyHashMap.put(columnDesc[5], columnDesc[6]);
                    relationships.put(tableRank.get(tableName), tableRank.get(columnDesc[5]));
                }
            }
            dependencyGraph.put(tableName, dependencyHashMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}