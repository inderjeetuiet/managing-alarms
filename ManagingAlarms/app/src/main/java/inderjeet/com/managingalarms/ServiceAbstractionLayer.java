package inderjeet.com.managingalarms;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import java.util.TimerTask;

/**
 * Created by isingh on 7/20/15.
 */
public abstract class ServiceAbstractionLayer extends TimerTask {

    public ServiceAbstractionLayer(MainService service, MainService.Target target)
    {
        this.service = service;
        this.target = target;
    }

    MainService service;
    public static MainService.Target target;
    IntentFilter filterRefreshUpdate;
}
