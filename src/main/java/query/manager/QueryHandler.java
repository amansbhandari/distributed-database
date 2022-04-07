package query.manager;

import query.container.CreateQuery;
import query.container.SqlType;
import query.response.Response;
import query.response.ResponseType;

/**
 *  Handles different queries from user
 *  Use this class to pass tokenised query
 */
public class QueryHandler
{

    public static Response executeQuery(Object query, SqlType sqlType)   //we will need to instantiate the
    {
        if(sqlType.equals(SqlType.CREATE))      //Handle create query
        {
            return CreateHandler.executeCreateQuery((CreateQuery) query);
        }
        else if(sqlType.equals(SqlType.SELECT))
        {

        }
        return new Response(ResponseType.INTERNAL_ERROR, "System error.");
    }
}
