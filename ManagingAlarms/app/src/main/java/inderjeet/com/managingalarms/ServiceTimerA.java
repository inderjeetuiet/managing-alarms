package inderjeet.com.managingalarms;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by isingh on 7/20/15.
 */
public class ServiceTimerA extends ServiceAbstractionLayer {

    private static String TAG = "ServiceTimerA";

    public ServiceTimerA(MainService service, MainService.Target target)
    {
        super(service, target);
    }

    public void run()
    {
        if (service != null)
        {
            Log.d(TAG, target + " enabled ");
        }
    }
}
