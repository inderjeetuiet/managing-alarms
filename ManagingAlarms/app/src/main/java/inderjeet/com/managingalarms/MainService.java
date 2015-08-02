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
            timer.scheduleAtFixedRate(new ServiceTimerA(MainService.this, target), DELAY_TIMER_TIME, TIMER_START_TIME);
            timer.scheduleAtFixedRate(new ServiceTimerB(MainService.this, target), DELAY_TIMER_TIME, TIMER_START_TIME);
            timer.scheduleAtFixedRate(new ServiceTimerC(MainService.this, target), DELAY_TIMER_TIME, TIMER_START_TIME);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags,startId);
        if(intent != null)
            setTimerInfo(intent.getStringExtra("DeviceMode"));
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
            timerReset();
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
                serviceHandler.removeMessages(0);
            }
            Message msgObj = serviceHandler.obtainMessage();
            serviceHandler.sendMessage(msgObj);
            runServiceInBackground();
        }
    }

    /**
     * Method to start the alarm manager to run the timertasks in background
     */

    private void runServiceInBackground()
    {
        final Intent restartIntent = new Intent(this, MainService.class);
        restartIntent.putExtra("ALARM_RESTART_SERVICE_DIED", true);
        final AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Handler restartServiceHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, restartIntent, 0);
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pintent);
                sendEmptyMessageDelayed(0, TIMER_START_TIME);
            }
        };
        restartServiceHandler.sendEmptyMessageDelayed(0, 0);
    }

    /**
     * It reset the timertask to null, that allows switching of mode easily
     */

    private void timerReset(){
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
