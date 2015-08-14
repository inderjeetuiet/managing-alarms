package inderjeet.com.managingalarms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.ArrayList;
import inderjeet.com.managingalarms.properties.*;
import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends ActionBarActivity
{

    private static String TAG = "MainActivity";
    dataReciever dataReciever = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataReciever = new dataReciever(null);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    /**
     * To start the Device mode when application starts
     */

    @Override
    protected void onStart()
    {
        Intent intent = new Intent(MainActivity.this, MainService.class);
        intent.putExtra("DeviceMode", "enable");
        intent.putExtra("receiver", dataReciever);
        startService(intent);
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    /**
     * To switch the mode from device to server, when app goes background
     */

    @Override
    protected void onStop()
    {
        Intent intent = new Intent(MainActivity.this, MainService.class);
        intent.putExtra("DeviceMode", "disable");
        startService(intent);
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    /**
     * result reciever to establish connection from timertask services
     */

    class dataReciever extends ResultReceiver {
        public dataReciever(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if(resultCode == 100){
                ArrayList<wifiProperty> wifi = (ArrayList<wifiProperty>)resultData.getSerializable("data");
            }
            else{
                Log.d(TAG, "else executed");
            }
        }
    }

}
