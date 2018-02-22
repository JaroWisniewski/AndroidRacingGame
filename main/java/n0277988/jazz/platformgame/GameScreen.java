package n0277988.jazz.platformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * Created by jaros on 10/02/2018.
 */

public class GameScreen extends SurfaceView implements SurfaceHolder.Callback/*, SensorEventListener*/ {
    private MainThread thread;
    private static final int SENSOR_DELAY = 500 * 1000; // 500ms
    private static final int FROM_RADS_TO_DEGS = -57;
   // Bitmap CharMap = BitmapFactory.decodeResource(getResources(), R.drawable.character);
    GameCharacter Player;
    Obstacle obsOne;
    Obstacle obsTwo;
    Point PlayerPoint;
    Point ObstaclePoint;
    Point ObstaclePoint2;
    Random random;
    private Wall_Manager Wall_Manager;
    public SensorManager SensMan;
    public Sensor Sensor;
    private Context Context;
    private int sideMovement = 0;
    private int dMovement;

    public GameScreen(Context context) {
        super(context);

        Context = context;

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        /*
        SensMan = (SensorManager) Context.getSystemService(Context.SENSOR_SERVICE);
        Sensor = SensMan.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        SensMan.registerListener(this, Sensor, SENSOR_DELAY);
*/
        sideMovement = getWidth()/2;
        PlayerPoint = new Point(sideMovement, getHeight()/2);
        Player = new GameCharacter(new Rect(20, 20, 200, 200), Color.rgb(0,0,255));

        int gap = 4*Constants.Screen_Width/5;
        Wall_Manager = new Wall_Manager(gap, 800,10, Player);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();

        sideMovement = getWidth()/2;
        PlayerPoint = new Point(sideMovement, getHeight()/2);
        Player = new GameCharacter(new Rect(20, 20, 200, 200), Color.rgb(0,0,255));


        ObstaclePoint = new Point(300, 0);
        ObstaclePoint2 = new Point(600, 0);
        obsTwo = new Obstacle(new Rect(100, 100, 200 , 200), Color.rgb(255,0,0));
        obsOne = new Obstacle(new Rect(100, 100, 200, 200), Color.rgb(255,0,0));


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

    public boolean collisionCheck() {
        if (obsOne.playerCollision(Player) || obsTwo.playerCollision(Player)) {
            return true;
        }
        return false;
    }

    public void update() {
        if (Wall_Manager.collisionCheck(Player) || collisionCheck()) {
            Player.setColor(Color.rgb(255, 0, 0));
        } else //(Wall_Manager.WallcollisionCheck(Player) == false){
        {
            Player.setColor(Color.rgb(255, 255, 255));
        }/*
        else {
            Player.setColor(Color.rgb(0, 255, 0));
        }*/
        if (ObstaclePoint.y < getHeight() - 10) {
            ObstaclePoint.y += 10;
        } else {
            ObstaclePoint.y = 0;
            random = new Random();
            ObstaclePoint.x = random.nextInt(getWidth());
        }
        if (ObstaclePoint2.y < getHeight() - 10) {
            ObstaclePoint2.y += 10;
        } else {
            ObstaclePoint2.y = 0;
            random = new Random();
            ObstaclePoint2.x = random.nextInt(getWidth());
        }
        Player.update(PlayerPoint);
        obsOne.update(ObstaclePoint);
        obsTwo.update(ObstaclePoint2);
        Wall_Manager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Paint Background = new Paint(Color.BLACK);

        canvas.drawRect(0, 0, getWidth(), getHeight(), Background);

        Wall_Manager.draw(canvas);

        obsOne.draw(canvas);

        obsTwo.draw(canvas);

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

    }

}
 -------------------------- Character Example Function -------------------------
    public class Character {
            public int posX;
            public int posY;
            private Bitmap character_bitmap;
            public boolean jump;

            public Character(Bitmap map, int x, int y) {
                posX = x;
                posY = y;
                character_bitmap = map;
            }

            public int getWidth() {
                return character_bitmap.getWidth();
            }

            public int getHeight() {
                return character_bitmap.getHeight();
            }

            public void drawCharacter(Canvas canvas) {
                canvas.drawBitmap(character_bitmap, posX - getWidth() / 2, posY - getHeight() / 2, new Paint());
            }

        }

    }*/


