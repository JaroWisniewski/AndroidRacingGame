package n0277988.jazz.platformgame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by jaros on 13/02/2018.
 *
 * Main Thread Responsible for updating the game screen, drawing and limiting the frame rate
 */

public class MainThread extends Thread {
    public static final int MAX_FPS = 60;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GameScreen gameScreen;
    private boolean running;
    public static Canvas canvas;


    public MainThread(SurfaceHolder srfcHolder, GameScreen gameScrn) {
        super();
        this.surfaceHolder = srfcHolder;
        this.gameScreen = gameScrn;
    }

    public void setRunning (boolean run){

        this.running = run;
    }


    public boolean isRunning(){
        return running;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis = 0;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS; //time of one frame time (min) draw in milliseconds

        while (running)
        {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                    this.gameScreen.update();
                    this.gameScreen.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            catch(Exception e)
                {
                    e.printStackTrace();
                }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try
            {
                if (waitTime > 0)
                {
                    this.sleep(waitTime);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == MAX_FPS)
            {
                averageFPS = 1000000000 * (double) frameCount  / (double) totalTime;

                Log.d("Average FPS", Double.toString(averageFPS)); // For quality control
                frameCount = 0;
                totalTime = 0;

            }
        }
    }
}
