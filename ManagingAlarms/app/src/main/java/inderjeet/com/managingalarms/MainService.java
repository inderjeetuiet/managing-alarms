package inderjeet.com.managingalarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;

import java.util.Timer;

/**
 * Created by isingh on 7/20/15.
 */

public class MainService extends Service
{

    Timer timer;
    Target target = null;
    private int DELAY_TIMER_TIME = 0;
    private int TIMER_START_TIME = 600000;
    static AlarmManager alarmMgr;
    PendingIntent pintent;
    private static final String TAG = "MainService";

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    /*
    handler to start the timerTasks
     */

    public final Handler serviceHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            timer = new Timer();
            if (target == null) {
                target = Target.SERVER;
            }
            timer.scheduleAtFixedRate(new ServiceWiFi(MainService.this, target), DELAY_TIMER_TIME, TIMER_START_TIME);
            timer.scheduleAtFixedRate(new ServiceTimerB(MainService.this, target), DELAY_TIMER_TIME, TIMER_START_TIME);
            timer.scheduleAtFixedRate(new ServiceTimerC(MainService.this, target), DELAY_TIMER_TIME, TIMER_START_TIME);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags,startId);

        if(intent != null)
        {
            ResultReceiver ss = intent.getParcelableExtra("receiver");
            if(ss != null)
                MainApplication.setReciever(ss);
            setTimerInfo(intent.getStringExtra("DeviceMode"));
        }
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent)
    {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    /**
     * method handles the switch between mode from Device to Server or vice versa
     * @param check : String variable to swith mode, enable = Device mode & disable = Server mode
     */

    private void setTimerInfo(String check)
    {
        if(check != null)
        {
            if (check.equals("enable"))
            {
                target = Target.DEVICE;
                DELAY_TIMER_TIME = 0;
                TIMER_START_TIME = 5000;
            }
            if (check.equals("disable"))
            {
                target = Target.SERVER;
                DELAY_TIMER_TIME = 0;
                TIMER_START_TIME = 600000;
            }
            timerReset();
            if(target.equals(Target.DEVICE)) {
                alarmMgr.cancel(pintent);
                Message msgObj = serviceHandler.obtainMessage();
                serviceHandler.sendMessage(msgObj);
            } else {
                runServiceInBackground();
            }
        }
    }

    /**
     * Method to start the alarm manager to run the timertasks in background
     */

    private void runServiceInBackground()
    {
        final Intent restartIntent = new Intent(this, MainService.class);
        restartIntent.putExtra("ALARM_RESTART_SERVICE_DIED", true);
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Handler restartServiceHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, restartIntent, 0);
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), TIMER_START_TIME, pintent);
                sendEmptyMessageDelayed(0, TIMER_START_TIME);
            }
        };
        restartServiceHandler.sendEmptyMessageDelayed(0, 0);
    }

    /**
     * It reset the timertask to null, that allows switching of mode easily
     */

    private void timerReset()
    {
        if(null != timer)
        {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    /**
     * Enum to know which mode is enable at the time, in the application DEVICE or SERVER
     */

    public enum Target
    {
        SERVER,
        DEVICE
    }
}
