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
import android.widget.TextView;

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
    private String Name = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("NAME")) {
            Name = getIntent().getStringExtra("NAME");
        }

        Score = new DatabaseManager(this);

        SP = new SoundPlayer(this);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        menuSong = MediaPlayer.create(this, R.raw.intro_music);
        menuSong.start();

        setContentView(R.layout.activity_main__menu);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(Name);
        SP.playMenu();

    }

    @Override
    protected void onStart() {
        super.onStart();
        menuSong.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        menuSong.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    public void onClickStart(View view){
            SP.playMenu();
            Intent start = new Intent(this, MainActivity.class);
            start.putExtra("NAME", Name);
            startActivity(start);
            SP.setSoundPause();
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

    public void onClickName (View v){
        Intent name = new Intent(this, Name.class);
        startActivityForResult(name,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Name = data.getStringExtra("NAME");
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(Name);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }//onActivityResult




}
