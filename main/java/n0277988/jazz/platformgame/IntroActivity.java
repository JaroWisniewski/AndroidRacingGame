package n0277988.jazz.platformgame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import java.net.URI;


public class IntroActivity extends Activity {
    private VideoView Vw;
    private MediaPlayer Intro = null;
    private Context activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

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

        Vw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Vw.stopPlayback();
                Intro.stop();
                Intro.release();
                Intro = null;
                Intro = MediaPlayer.create(activity, R.raw.intro_finish);
                Intro.start();
                return true;
            }
        });

        Vw.setOnCompletionListener(new   MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                Vw.stopPlayback();
                Intro.stop();
                Intro.release();
                Intro = null;
                Intro = MediaPlayer.create(activity, R.raw.intro_finish);
                Intro.start();
            }
        });
    }
}
