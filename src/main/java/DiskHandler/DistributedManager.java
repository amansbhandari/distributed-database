package DiskHandler;

import utils.UtilsConstant;
import utils.UtilsFileHandler;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DistributedManager
{

    private static final String REMOTE_DATABASE_PATH = "/home/amansbhandari/dmwaproject/";
    /**
     * returns which instance does a file exists. -1 when it doesn't exists in either
     * @param database
     * @param filename name of the file you are looking
     * @return
     */
    private static String whichInstance(String database , String filename)
    {
        String fullPathGM = UtilsConstant.DATABASE_ROOT_FOLDER+"/"+ database + "/"+ UtilsConstant.GM_FILE_NAME;
        try {
            List<String> content = UtilsFileHandler.readFile(fullPathGM);

            for(String line : content)
            {
                String[] elements = line.split("[|]");
                if(elements[0].equals(filename))
                {
                    return elements[1];
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "-1";
    }

    /**
     * reads and return the content of the file line by line. Caller doesn't need to care about which instance the file is located.
     * @param database
     * @param fullpath path of the file you are looking for. For e.g. database/University/metadata_students.txt
     * @param filename just the file name with extension you are looking for. For e.g. metadata_students.txt
     * @return
     */
    public static List<String> readFile(String database, String fullpath, String filename)
    {
        String instanceOfFile = whichInstance(database, filename);
        try {
            List<String> myInstance = UtilsFileHandler.readFile("instances/local.txt");
            List<String> otherInstance = UtilsFileHandler.readFile("instances/remote.txt");

            if(instanceOfFile.equals(myInstance.get(0)) || filename.startsWith("global_") || fullpath.startsWith("logs"))    //The file is in current instance
            {
                return UtilsFileHandler.readFile(fullpath);
            }

            fullpath =  REMOTE_DATABASE_PATH + fullpath;
            //The file is in remote instance
            return RemoteHandler.executeCommand("cat "+fullpath , otherInstance.get(1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * reads and return the content of the file line by line. Caller doesn't need to care about which instance the file is located.
     * @param database
     * @param fullpath path of the file you are looking for. For e.g. database/University/metadata_students.txt
     * @param filename just the file name with extension you are looking for. For e.g. metadata_students.txt
     * @return
     */
    public static Boolean writeFile(String database, String fullpath, String filename, String content)
    {
        String instanceOfFile = whichInstance(database, filename);
        try {
            List<String> myInstance = UtilsFileHandler.readFile("instances/local.txt");
            List<String> otherInstance = UtilsFileHandler.readFile("instances/remote.txt");

            if(instanceOfFile.equals(myInstance.get(0)) || filename.startsWith("global_") || fullpath.startsWith("logs"))    //The file is in current instance
            {
                UtilsFileHandler.writeToFile(fullpath, content);

                if(!filename.startsWith("global_") && !fullpath.startsWith("logs"))
                    return true;

            }

            fullpath =  REMOTE_DATABASE_PATH + fullpath;
            //The file is in remote instance.. write it
            RemoteHandler.executeCommand("echo -e"  + "\""+ content + "\""+ "> " + fullpath , otherInstance.get(1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
