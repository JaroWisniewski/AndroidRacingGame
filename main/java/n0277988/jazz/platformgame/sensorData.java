package n0277988.jazz.platformgame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by jaros on 26/02/2018.
 */

public class sensorData implements SensorEventListener {
     private static final int SENSOR_DELAY = 500 * 1000; // 500ms
     private static final int FROM_RADS_TO_DEGS = -57;
     private SensorManager SensMan;
     private Sensor magnet ;
     private Sensor accelerometer;

     private float[] accData;
     private float[] magData;

     private float[] orientation;
     public float[] startOrientation = null;

     public float[] getOrientation(){
         return orientation;
    }

    public float[] startOrientation()
    {
         startOrientation = getOrientation();
         return startOrientation;
    }

    public void startGame()
    {
        startOrientation = null;
    }

    public void sensorInitialization() {
        SensMan = (SensorManager) Constants.context.getSystemService(Context.SENSOR_SERVICE);
        magnet = SensMan.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = SensMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensMan.registerListener(this, magnet, SensorManager.SENSOR_DELAY_GAME);
        SensMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
