package inderjeet.com.managingalarms;

import android.util.Log;

/**
 * Created by isingh on 7/20/15.
 */
public class ServiceTimerC extends ServiceAbstractionLayer {
    private static String TAG = "ServiceTimerC";

    public ServiceTimerC(MainService service, MainService.Target target)
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
