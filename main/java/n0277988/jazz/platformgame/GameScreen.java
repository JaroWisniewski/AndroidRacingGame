package n0277988.jazz.platformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

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
    private GameCharacter Player;
    private Background Road;
    private Point PlayerPoint;
    private Random random;
    private Wall_Manager Wall_Manager;
    private Context Context;
    private int sideMovement = 0;

    public GameScreen(Context context) {
        super(context);

        Context = context;

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        Bitmap arrow = Bitmap.createScaledBitmap(Arrow, 100, 100, true);
        Bitmap finishSmall = Bitmap.createScaledBitmap(finish, 256,256,true);
        sideMovement = getWidth()/2;
        PlayerPoint = new Point(sideMovement, getHeight()/2);
        Player = new GameCharacter(new Rect(20, 20, 200, 200), Color.rgb(0,0,255), CharMap);

        Road = new Background(asphalt);

        int gap = 4*Constants.Screen_Width/5;
        Wall_Manager = new Wall_Manager(gap, grass.getHeight(),30, Player, grass, finishSmall, arrow);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();

        Bitmap CharTextSmall = Bitmap.createScaledBitmap(CharMap, 180, 180, true);
        sideMovement = getWidth()/2;
        PlayerPoint = new Point(sideMovement, getHeight()/2);
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
/*
    // ------------------- Sensor movement change ----------------------------
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == Sensor) {
            if (event.values.length > 4) {
                float[] truncatedRotationVector = new float[4];
                System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4);
                updatePitch(truncatedRotationVector);

                PlayerPoint.set(sideMovement ,  (getHeight()/6*5) );
            } else {
                updatePitch(event.values);
                 PlayerPoint.set(sideMovement , getHeight()/6*5);
            }

        }
    }

    // ----------------- Accuracy of Rotation Sensor ------------------------
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void updatePitch(float[] vectors) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisZ = SensorManager.AXIS_Z;
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisZ, adjustedRotationMatrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        float pitch = 90.0f + (orientation[1] * FROM_RADS_TO_DEGS);
        float roll = orientation[2] * FROM_RADS_TO_DEGS;


        if(roll<0)
        {roll=-1;}
        else
        {roll=1;}

        pitch = pitch * roll;
       // int NewSideMovement = (int) ;
        if(pitch > 70)
        {
            dMovement = -20;
        }
        else if (pitch < 70 && pitch > 15)
        {
            dMovement = -10;
        }
        else if (pitch < 15 && pitch > -15)
        {
            dMovement = 0;
        }
        else if(pitch < -15 && pitch > - 70)
        {
            dMovement = 10;
        }
        else
        {
            dMovement = 20;
        }
        sideMovement += dMovement;

      //  sideMovement = (int) (getWidth()/2 - (pitch * roll) * (getWidth() / 60));
        Log.d("Pitch", Float.toString(pitch));
        Log.d("Roll", Float.toString(roll));
        Log.d("Move", Float.toString(sideMovement));

    }*/

