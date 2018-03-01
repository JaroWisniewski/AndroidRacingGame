package n0277988.jazz.platformgame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Jaroslaw on 27/02/2018.
 */

public class SoundPlayer {
    private static SoundPool soundEngine;
    private static SoundPool soundPool;
    private static int engine;
    private static int boost;
    private static int cheer;
    private static int menu;

    public SoundPlayer(Context context){

        soundEngine = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        engine = soundEngine.load(context, R.raw.motor_loop, 1);
        boost = soundPool.load(context, R.raw.boost, 1);
        cheer = soundPool.load(context, R.raw.cheer, 2);
        menu = soundPool.load(context, R.raw.intro_finish,3);
    }

    public void playEngine(){

        if (soundEngine != null)
        {soundEngine.play(engine, 1.0f, 1.0f, 1,-1,1.0f);}
    }

    public void playBoost(){
        soundPool.play(boost, 1.0f, 1.0f, 1,0,1.0f);
    }

    public void playCheer(){
        soundPool.play(cheer, 1.0f, 1.0f, 1,0,1.0f);
    }

    public void playMenu(){
        soundPool.play(menu, 1.0f, 1.0f, 1,0,1.0f);
    }

    public void setSoundPause (){
        soundPool.autoPause();
        soundEngine.autoPause();
    }

    public void autoResume(){
        soundPool.autoResume();
        soundEngine.autoResume();
    }
}
