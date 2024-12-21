package com.example.lightsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView light,lightTp;
    private LinearLayout main;
    private Sensor lightSensor;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main=findViewById(R.id.main);
        light=findViewById(R.id.lightLevel);
        lightTp=findViewById(R.id.lightType);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager!= null){
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float light_val = sensorEvent.values[0];
        light.setText("Light Level = " + light_val);
        String lightCondition;

        if (light_val < 10) {
            lightCondition = "dark";
            main.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
        } else if (light_val < 1000) {
            lightCondition = "dim";
            main.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_gray));
        } else {
            lightCondition = "light";
            main.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        }

        lightTp.setText("Light Type = " + lightCondition);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}