package n0277988.jazz.platformgame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * Intro - Displays the game intro.
 */

public class IntroActivity extends Activity {

    private VideoView Vw;
    private MediaPlayer Intro = null;
    private Intent Menu;
    private boolean mIsClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        setContentView(R.layout.activity_intro);

        Intro = MediaPlayer.create(this, R.raw.intro_music);

        Intro.setVolume(1f, 1f);

        Intro.start();

        Vw = findViewById(R.id.introVideo);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.intro;
        Uri uri = Uri.parse(path);
        Vw.setVideoURI(uri);
        Vw.start();

        Menu = new Intent(this, Main_Menu.class);


        Vw.setOnTouchListener(new View.OnTouchListener() {
                                  @Override
                                  public boolean onTouch(View v, MotionEvent event) {
                                      if (!mIsClicked) {
                                          mIsClicked = true;
                                          startActivity(Menu);
                                          return true;
                                      }
                                      return false;
                                  }
                              }
        );

        Vw.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!mIsClicked) {
                    mIsClicked = true;
                    startActivity(Menu);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        mIsClicked = false;
    }


    @Override
    protected void onPause() {
        super.onPause();
        Intro.release();
        Vw.stopPlayback();
        onStop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onDestroy();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        Vw = null;
        Intro = null;
    }
}
