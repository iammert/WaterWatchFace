package co.mobiwise.watchfacewater;

import android.app.Activity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Created by mertsimsek on 14/06/15.
 */
public class WatchfaceActivity extends Activity {

    WaterView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (WaterView) findViewById(R.id.water_view);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.updateTime();
                    }
                });
            }
        }, 0, 200);
    }
}
