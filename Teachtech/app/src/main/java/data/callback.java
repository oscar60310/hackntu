package data;

import org.json.JSONObject;

/**
 * Created by Goofy on 2016/8/20.
 */
public class callback
{
    public interface AsyncResponse{void processFinish(JSONObject output);}
}
