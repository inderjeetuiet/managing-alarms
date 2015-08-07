package inderjeet.com.managingalarms;

import android.app.Application;
import android.content.Context;
import android.os.ResultReceiver;

/**
 * Created by isingh on 8/3/15.
 */
public class MainApplication extends Application
{
    private static Context instance;
    private static ResultReceiver resultReceiver;

    @Override public void onCreate()
    {
        super.onCreate();
        instance = getApplicationContext();
    }

    public static void setReciever(ResultReceiver rReceiver){
        resultReceiver = rReceiver;
    }

    public static Context get() {
        return MainApplication.instance;
    }

    public static ResultReceiver getResultReceiver(){
        return resultReceiver;
    }
}
