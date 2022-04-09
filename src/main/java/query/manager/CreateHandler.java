package query.manager;

import localmetadata.LocalMetdataHandler;
import utils.UtilsConstant;
import query.container.CreateQuery;
import query.response.Response;
import query.response.ResponseType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class CreateHandler {

    public static Response executeCreateQuery(CreateQuery createQuery)
    {
        try {
            return LocalMetdataHandler.createTableMetadata(createQuery,UtilsConstant.DATABASE_ROOT_FOLDER + "/" + createQuery.getDatabase() + "/");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new Response(ResponseType.INTERNAL_ERROR, "System error.");
    }
}
