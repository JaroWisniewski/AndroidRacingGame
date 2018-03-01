package n0277988.jazz.platformgame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Main_Menu extends Activity {

    private MediaPlayer menuSong = null;
    private SoundPlayer SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SP = new SoundPlayer(this);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        menuSong = MediaPlayer.create(this, R.raw.intro_music);
        menuSong.start();

        setContentView(R.layout.activity_main__menu);

    }

        public void onClickStart(View view){
            SP.playMenu();
            Intent start = new Intent(this, MainActivity.class);
            startActivity(start);
            SP.setSoundPause();
            menuSong.release();
    }

    public void onClickQuit(View view){
        SP.playMenu();
        SP.setSoundPause();
        menuSong.release();
        finish();
        System.exit(0);


    }




}
