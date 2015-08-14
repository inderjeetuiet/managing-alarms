package inderjeet.com.managingalarms;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import inderjeet.com.managingalarms.properties.*;

import java.util.ArrayList;

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

    public void putData(ArrayList<wifiProperty> data){
        resultReceiver = MainApplication.getResultReceiver();
        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            if(resultReceiver != null  && data != null) {
                bundle.putSerializable("data", data);
                resultReceiver.send(100, bundle);
            }
        }
    }
}
