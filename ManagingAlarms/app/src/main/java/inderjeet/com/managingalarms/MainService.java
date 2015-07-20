package inderjeet.com.managingalarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import java.util.Timer;

/**
 * Created by isingh on 7/20/15.
 */
public class MainService extends Service {
    Timer timer;
    Target target = null;
    private int DELAY_TIMER_TIME = 0;
    private int TIMER_START_TIME = 600000;
    private int resetAlarmTimer = 600000;
    private static final String TAG = "MainService";

    @Override public void onCreate()
    {
        super.onCreate();
    }

    public final Handler serviceHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            timer = new Timer();

            if (target == null) {
                target = Target.SERVER;
            }
            timer.scheduleAtFixedRate(new ServiceTimerA(MainService.this, target), DELAY_TIMER_TIME, TIMER_START_TIME);

        }
    };

    @Override public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags,startId);
        setTimerInfo(intent.getStringExtra("DeviceMode"));
        return START_STICKY;
    }

    @Override public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    @Override public void onRebind(Intent intent)
    {
        super.onRebind(intent);
    }

    @Override public void onDestroy()
    {
        super.onDestroy();
    }

    private void setTimerInfo(String check)
    {
        if(check != null)
        {
            if (check.equals("enable"))
            {
                target = Target.DEVICE;
                DELAY_TIMER_TIME = 0;
                TIMER_START_TIME = 5000;
                timerReset();
                Message msgObj = serviceHandler.obtainMessage();
                serviceHandler.sendMessage(msgObj);
            }
            if (check.equals("disable"))
            {
                target = Target.SERVER;
                DELAY_TIMER_TIME = 0;
                TIMER_START_TIME = 600000;
                timerReset();
                serviceHandler.removeMessages(0);
                Message msgObj = serviceHandler.obtainMessage();
                serviceHandler.sendMessage(msgObj);
                esServiceRunningBackground();
            }
        }
    }
    private void esServiceRunningBackground()
    {
        final Intent restartIntent = new Intent(this, MainService.class);
        restartIntent.putExtra("ALARM_RESTART_SERVICE_DIED", true);
        final AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Handler restartServiceHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, restartIntent, 0);
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), resetAlarmTimer, pintent);
                sendEmptyMessageDelayed(0, resetAlarmTimer);
            }
        };
        restartServiceHandler.sendEmptyMessageDelayed(0, 0);
    }
    private void timerReset(){
        if(null != timer)
        {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
    public enum Target
    {
        SERVER,
        DEVICE
    }
}
