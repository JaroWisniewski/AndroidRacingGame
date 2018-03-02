package n0277988.jazz.platformgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Main Menu activity
 *
 * Quit
 * High Score
 * Start
 */

public class Main_Menu extends Activity {

    private MediaPlayer menuSong = null;
    private SoundPlayer SP;
    private DatabaseManager Score;
    private String Name = "Jaro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Score = new DatabaseManager(this);

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

    public void onClickScore(View v){
        Cursor resource = Score.scoreCard();
        StringBuffer buffer = new StringBuffer();
        while (resource.moveToNext())
        {
            buffer.append("Name: " + resource.getString(0) + "  ");
            buffer.append("Score: " + resource.getString(1) + "\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("HIGH SCORE");
        builder.setMessage(buffer.toString());
        builder.show();
    }

    public void changeName (){

    }




}
