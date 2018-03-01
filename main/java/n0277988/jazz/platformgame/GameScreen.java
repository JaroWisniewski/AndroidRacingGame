package n0277988.jazz.platformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by jaros on 10/02/2018.
 */

public class GameScreen extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private Bitmap grass = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
    private Bitmap asphalt = BitmapFactory.decodeResource(getResources(), R.drawable.asphalt);
    private Bitmap finish = BitmapFactory.decodeResource(getResources(), R.drawable.finish);
    private Bitmap Arrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
    private Paint UI;
    private MediaPlayer Mp = null;

    private sensorData Sensor;
    private long frameTime;

    public SoundPlayer soundPlayer;
    private GameCharacter Player;
    private Background Road;
    private Point PlayerPoint;
    private Wall_Manager Wall_Manager;

    public GameScreen(Context context) {
        super(context);

        soundPlayer = new SoundPlayer(Constants.context);

        Mp = MediaPlayer.create(Constants.context, R.raw.game);
        Mp.setVolume(0.5f, 0.5f);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        Sensor = new sensorData();
        Sensor.sensorInitialization();
        frameTime = System.currentTimeMillis();

        Bitmap arrow = Bitmap.createScaledBitmap(Arrow, 100, 100, true);
        Bitmap finishSmall = Bitmap.createScaledBitmap(finish, 256,256,true);

        PlayerPoint = new Point(getWidth()/2, 3*getHeight()/4);
        Player = new GameCharacter(new Rect(20, 20, 200, 200), Constants.context);

        Road = new Background(asphalt);

        int gap = 4*Constants.Screen_Width/5;
        Wall_Manager = new Wall_Manager(gap, grass.getHeight(),30, Player, grass, finishSmall, arrow, soundPlayer);

        UI = new Paint();

        UI.setColor(Color.BLUE);

        UI.setStyle(Paint.Style.FILL_AND_STROKE);

        UI.setTextSize(90);

        soundPlayer.playEngine();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        Constants.Start_Time = System.currentTimeMillis();

        thread.setRunning(true);
        thread.start();

        PlayerPoint = new Point(getWidth()/2, 3*getHeight()/4);
        Player = new GameCharacter(new Rect(20, 20, 200, 200), Constants.context);

        soundPlayer.playEngine();
        Mp.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        soundPlayer.autoResume();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
        soundPlayer.setSoundPause();
        Mp.stop();
        Mp.release();
        Mp = null;

    }


// -------------------- Touch Event ----------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                PlayerPoint.set((int) event.getX(), (int) event.getY());
                return true;
        }
        return false;
    }

    public void update() {
        if ( Wall_Manager.boostCheck(Player))
        {
            Wall_Manager.increaseSpeed();
        }
        if (Wall_Manager.collisionCheck(Player))
        {
            Wall_Manager.decreaseSpeed();
        }


        if(frameTime < Constants.Start_Time)
        {
            frameTime = Constants.Start_Time;
        }

        int elapsedTime = (int) (System.nanoTime() - frameTime);

        frameTime = System.nanoTime();

        if(!Wall_Manager.isLevelFinished())

        {
            if (Sensor.getOrientation() != null && Sensor.startOrientation != null) {
                float roll = Sensor.getOrientation()[2] - Sensor.startOrientation[2];

                float x_move = roll * Constants.Screen_Width / 250000000f;

                PlayerPoint.x += Math.abs(x_move * elapsedTime) > 5 ? x_move * elapsedTime : 0;
            }

            if (PlayerPoint.x < 0) {
                PlayerPoint.x = 0;
            } else if (PlayerPoint.x > Constants.Screen_Width) {
                PlayerPoint.x = Constants.Screen_Width;
            }
        }

        Player.update(PlayerPoint);

        Road.move(Wall_Manager.getSpeed());

        Wall_Manager.update();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Road.draw(canvas);

        Wall_Manager.draw(canvas);

        Player.draw(canvas);

        if(Wall_Manager.isStart()) {
            canvas.drawText("TIME : ", getWidth()/2 - 350,  getHeight() / 10, UI);
            canvas.drawText(Double.toString(Wall_Manager.getLvlTime()), getWidth()/2,  getHeight() / 10, UI);
        }

    }
}
