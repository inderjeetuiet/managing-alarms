package inderjeet.com.managingalarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by isingh on 7/20/15.
 */
public class ServiceWiFi extends ServiceAbstractionLayer {

    private static String TAG = "ServiceTimerA";

    public ServiceWiFi(MainService service, MainService.Target target)
    {
        super(service, target);
        this.manager = (WifiManager)(this.service.getSystemService(Context.WIFI_SERVICE));
        this.receiver = new WifiReceiver();
    }

    public void run()
    {
        if (service != null)
        {
            if (this.manager != null) {
                if(manager.isWifiEnabled())
                    manager.startScan();
                else
                    Log.d(TAG, "WiFi disbaled");
            }
        }
    }
    class WifiReceiver extends BroadcastReceiver
    {
        public void onReceive(Context c, Intent intent) {
            wifi = new ArrayList<>();
            List<ScanResult> results = manager.getScanResults();
            try {
                if (results != null) {
                    for (ScanResult result : results) {
                        if (result.level != 0)
                            wifi.add(result.SSID);
                    }
                }
            } catch (Exception excpetion) {
                excpetion.printStackTrace();
            } finally {
                if(target.equals(ServiceAbstractionLayer.target.DEVICE)){
                    //ToDo
                }
                service.unregisterReceiver(receiver);
                results.clear();
            }
        }

    }

    // ///////////////////////////////////////////////////////////////
    ArrayList<String> wifi;
    private WifiManager manager;
    private WifiReceiver receiver;
}
