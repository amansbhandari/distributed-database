package localmetadata;

import query.container.CreateQuery;
import query.response.Response;
import query.response.ResponseType;
import utils.UtilsConstant;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class LocalMetdataHandler {

    public static Response createTableMetadata(CreateQuery createQuery, String path) throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer = new PrintWriter(path + UtilsConstant.PREFIX_LOCAL_METADATA + createQuery.getTableName() + ".txt", "UTF-8");
        //PrintWriter writer = new PrintWriter("aman.txt", "UTF-8");

        for(int i = 0 ;i<createQuery.getColumns().size();i++)
        {
            String line = createQuery.getColumns().get(i) + UtilsConstant.SEPERATOR+
                    createQuery.getColumnsDataType().get(i)+UtilsConstant.SEPERATOR+
                    createQuery.getColumnsNotNullStatus().get(i);

            if(createQuery.getColumns().get(i).equals(createQuery.getPrimaryKey()))
            {
                line += UtilsConstant.SEPERATOR + "PK";
            }

            if(createQuery.getColumns().get(i).equals(createQuery.getForeignKey()))
            {
                line += UtilsConstant.SEPERATOR + "FK"+
                        UtilsConstant.SEPERATOR + createQuery.getForeignKeyRefTable()+
                        UtilsConstant.SEPERATOR + createQuery.getForeignKeyRefCol();
            }

            writer.println(line);

        }
        writer.close();
        return new Response(ResponseType.SUCCESS,"Query OK, 0 rows affected");
    }
}
