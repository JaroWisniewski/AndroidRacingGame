package n0277988.jazz.platformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by jaros on 10/02/2018.
 */

public class GameScreen extends SurfaceView implements SurfaceHolder.Callback/*, SensorEventListener*/ {
    private MainThread thread;

    private Bitmap CharMap = BitmapFactory.decodeResource(getResources(), R.drawable.car);
    private Bitmap grass = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
    private Bitmap asphalt = BitmapFactory.decodeResource(getResources(), R.drawable.asphalt);
    private Bitmap finish = BitmapFactory.decodeResource(getResources(), R.drawable.finish);
    private Bitmap Arrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);

    private sensorData Sensor;
    private long frameRate;

    private GameCharacter Player;
    private Background Road;
    private Point PlayerPoint;
    private Wall_Manager Wall_Manager;

    public GameScreen(Context context) {
        super(context);


        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        Sensor = new sensorData();
        Sensor.sensorInitialization();
        frameRate = System.currentTimeMillis();

        Bitmap arrow = Bitmap.createScaledBitmap(Arrow, 100, 100, true);
        Bitmap finishSmall = Bitmap.createScaledBitmap(finish, 256,256,true);

        PlayerPoint = new Point(getWidth()/2, getHeight()/2);
        Player = new GameCharacter(new Rect(20, 20, 200, 200), Color.rgb(0,0,255), CharMap);

        Road = new Background(asphalt);

        int gap = 4*Constants.Screen_Width/5;
        Wall_Manager = new Wall_Manager(gap, grass.getHeight(),30, Player, grass, finishSmall, arrow);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        Constants.Start_Time = System.currentTimeMillis();

        thread.setRunning(true);
        thread.start();

        Bitmap CharTextSmall = Bitmap.createScaledBitmap(CharMap, 180, 180, true);
        PlayerPoint = new Point(getWidth()/2, getHeight()/2);
        Player = new GameCharacter(new Rect(20, 20, 200, 200), Color.rgb(0,0,255), CharTextSmall);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

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
        if ( Wall_Manager.boostCheckO(Player))
        {
            Wall_Manager.increaseSpeed();
        }
        if (Wall_Manager.collisionCheck(Player))
        {
            Player.setColor(Color.rgb(255, 0, 0));
            Wall_Manager.decreaseSpeed();
        }
        else
        {
            Player.setColor(Color.rgb(255, 255, 255));
        }

        if(frameRate < Constants.Start_Time)
        {
            frameRate = Constants.Start_Time;
        }

        int elapsedTime = (int) (System.nanoTime() - frameRate);

        frameRate = System.nanoTime();

        if(Sensor.getOrientation() != null && Sensor.startOrientation != null)
        {
            float roll = Sensor.getOrientation()[2] - Sensor.startOrientation[2];

            float x_move = roll * Constants.Screen_Width/250000000f;

            PlayerPoint.x += Math.abs(x_move*elapsedTime) > 5 ? x_move*elapsedTime : 0 ;
        }

        if(PlayerPoint.x < 0)
        {
            PlayerPoint.x = 0;
        }
        else if (PlayerPoint.x > Constants.Screen_Width)
        {
            PlayerPoint.x = Constants.Screen_Width;
        }
        else
        {

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

    }
}
