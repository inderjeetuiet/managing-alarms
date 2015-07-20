package inderjeet.com.managingalarms;

import android.util.Log;

/**
 * Created by isingh on 7/20/15.
 */
public class ServiceTimerB extends ServiceAbstractionLayer{
    private static String TAG = "ServiceTimerB";

    public ServiceTimerB(MainService service, MainService.Target target)
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
