package n0277988.jazz.platformgame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
public GameScreen GamePanel;
private DatabaseManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics DM = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DM);
        Constants.Screen_Height = DM.heightPixels;
        Constants.Screen_Width = DM.widthPixels;
        Constants.Finish = 20; // TODO Increase the level length
        Constants.context = this;

        dm = new DatabaseManager(this);

        GamePanel = new GameScreen(this, dm);



        setContentView(GamePanel);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
