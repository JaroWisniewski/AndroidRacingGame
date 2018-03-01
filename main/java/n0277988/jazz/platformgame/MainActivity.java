package n0277988.jazz.platformgame;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
public GameScreen GamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics DM = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DM);
        Constants.Screen_Height = DM.heightPixels;
        Constants.Screen_Width = DM.widthPixels;
        Constants.Finish = 20;
        Constants.context = this;

        GamePanel = new GameScreen(this);

        setContentView(GamePanel);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); // added
    }
}
