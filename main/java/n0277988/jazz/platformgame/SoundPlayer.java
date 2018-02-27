package n0277988.jazz.platformgame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Jaroslaw on 27/02/2018.
 */

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int engine;
    private static int boost;

    public SoundPlayer(Context context){

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        engine = soundPool.load(context, R.raw.motor_loop, 1);
        boost = soundPool.load(context, R.raw.boost, 2);
    }

    public void playEngine(){
        soundPool.play(engine, 1.0f, 1.0f, 1,-1,1.0f);
    }

    public void playBoost(){
        soundPool.play(boost, 1.0f, 1.0f, 1,0,1.0f);
    }

    public void setSoundPause (){
        soundPool.autoPause();
    }

    public void autoResume(){
        soundPool.autoResume();
    }
}
