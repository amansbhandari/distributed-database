package localmetadata;

import query.container.CreateQuery;
import query.response.Response;
import query.response.ResponseType;
import utils.UtilsConstant;
import utils.UtilsMetadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class LocalMetdataHandler {

    public static Response createTableMetadata(CreateQuery createQuery, String path) throws FileNotFoundException, UnsupportedEncodingException {

        String filePath = path + UtilsConstant.PREFIX_LOCAL_METADATA + createQuery.getTableName() + ".txt";
        PrintWriter writer = new PrintWriter(filePath, "UTF-8");

        for(int i = 0 ;i<createQuery.getColumns().size();i++)
        {
            String line = createQuery.getColumns().get(i) + UtilsConstant.SEPERATOR+
                    createQuery.getColumnsDataType().get(i)+UtilsConstant.SEPERATOR+
                    createQuery.getColumnsNotNullStatus().get(i);

            line += UtilsConstant.SEPERATOR;

            if(createQuery.getColumns().get(i).equals(createQuery.getPrimaryKey()))
            {
                line += "PK";
            }

            line += UtilsConstant.SEPERATOR;

            if(createQuery.getColumns().get(i).equals(createQuery.getForeignKey()))
            {
                if(UtilsMetadata.fkRefExists(path + UtilsConstant.PREFIX_LOCAL_METADATA + createQuery.getForeignKeyRefTable() + ".txt",createQuery.getForeignKeyRefCol())){
                    line += "FK"+
                            UtilsConstant.SEPERATOR + createQuery.getForeignKeyRefTable()+
                            UtilsConstant.SEPERATOR + createQuery.getForeignKeyRefCol();
                }
                else
                {
                    File file = new File(filePath);
                    file.delete();
                    return new Response(ResponseType.FAILED, "Reference for foreign key "+createQuery.getForeignKeyRefTable() +"."+createQuery.getForeignKeyRefCol()+" doesn't exists");
                }
            }
            else
            {
                line += UtilsConstant.SEPERATOR +
                        UtilsConstant.SEPERATOR;
            }

            writer.println(line);

        }
        writer.close();
        return new Response(ResponseType.SUCCESS,"Query OK, 0 rows affected");
    }
}
