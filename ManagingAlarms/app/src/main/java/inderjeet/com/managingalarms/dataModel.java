package inderjeet.com.managingalarms;
import android.os.ResultReceiver;

/**
 * Created by isingh on 8/3/15.
 */
public class dataModel
{
    public ResultReceiver resultReceiver;
    dataModel dataModel;
    private static dataModel instance = null;
    protected dataModel(){}

    public static dataModel getInstance()
    {
        if(instance == null)
        {
            instance = new dataModel();
        }
        return instance;
    }
}
