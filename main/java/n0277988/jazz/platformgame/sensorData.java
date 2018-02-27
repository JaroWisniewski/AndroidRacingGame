package n0277988.jazz.platformgame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by jaros on 26/02/2018.
 * Motion Sensor controller
 */

public class sensorData implements SensorEventListener {
     private static final int SENSOR_DELAY = 500 * 1000; // 500ms
     private static final int FROM_RADS_TO_DEGS = -57;
     private SensorManager SensMan;
     private Sensor magnet ;
     private Sensor accelerometer;

     private float[] accData;
     private float[] magData;

     private float[] orientation = new float[3];
     public float[] startOrientation = null;


     public sensorData()
    {
        SensMan = (SensorManager) Constants.context.getSystemService(Context.SENSOR_SERVICE);
        magnet = SensMan.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = SensMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

     public float[] getOrientation()
     {
         return orientation;
    }

    public float[] startOrientation()
    {
         startOrientation = getOrientation();
         return startOrientation;
    }

    public void resetGame()
    {
        startOrientation = null;
    }



    public void sensorInitialization() {
        SensMan.registerListener(this, magnet, SensorManager.SENSOR_DELAY_GAME);
        SensMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause(){
         SensMan.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            accData = event.values;
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            magData = event.values;
        }
        if(accData != null && magData != null)
        {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, accData, magData);
            if (success)
            {
                SensorManager.getOrientation(R, orientation);
                if(startOrientation == null)
                {
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
