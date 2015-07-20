package inderjeet.com.managingalarms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class MainActivity extends ActionBarActivity {

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }
    @Override
    protected void onStart() {
        Intent intent = new Intent(MainActivity.this, MainService.class);
        intent.putExtra("DeviceMode", "enable");
        startService(intent);
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onStop() {
        changeServiceMode();
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    private void changeServiceMode(){
        Intent intent = new Intent(MainActivity.this, MainService.class);
        intent.putExtra("DeviceMode", "disable");
        startService(intent);
    }
}
