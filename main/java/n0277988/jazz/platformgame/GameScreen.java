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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by jaros on 10/02/2018.
 *
 * Game Screen Surface.
 * Creates Bitmaps, Game Objects, and main game thread.
 *
 */

public class GameScreen extends SurfaceView implements SurfaceHolder.Callback {
    //Game Thread
    private MainThread thread;

    // Bitmaps
    private Bitmap grass = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
    private Bitmap asphalt = BitmapFactory.decodeResource(getResources(), R.drawable.asphalt);
    private Bitmap finish = BitmapFactory.decodeResource(getResources(), R.drawable.finish);
    private Bitmap Arrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);

    //Timer
    private Paint UI;

    // Sensor related variables
    private sensorData Sensor;
    private long frameTime;

    //Sound Control
    private MediaPlayer Mp = null;
    public SoundPlayer soundPlayer;

    //Game Objects
    private GameCharacter Player;
    private Background Road;
    private Point PlayerPoint;
    private Wall_Manager Wall_Manager;

    //Database Management
    private DatabaseManager db;
    private String Name;

    public GameScreen(Context context, DatabaseManager data, String name) {
        super(context);

        this.Name = name;

        db = data;

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
        Wall_Manager = new Wall_Manager(gap, grass.getHeight(),30, Player, grass, finishSmall, arrow, soundPlayer, db, Name);

        UI = new Paint();

        UI.setColor(Color.BLUE);

        UI.setStyle(Paint.Style.FILL_AND_STROKE);

        UI.setTextSize(90);

        soundPlayer.playEngine();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        soundPlayer = new SoundPlayer(Constants.context);

        Mp = MediaPlayer.create(Constants.context, R.raw.game);
        Mp.setVolume(0.5f, 0.5f);

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
        grass = null;
        asphalt = null;
        finish = null;
        Arrow = null;
        Player = null;

    }


// -------------------- Touch Event ----------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
       //         PlayerPoint.set((int) event.getX(), (int) event.getY()); for testing purposes
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


        // to update frame time if user will change the screens

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
