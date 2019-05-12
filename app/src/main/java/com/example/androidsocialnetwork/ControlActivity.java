package com.example.androidsocialnetwork;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

public class ControlActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intentBateria = getBaseContext().registerReceiver(null, intentFilter);
        int estado = intentBateria.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        Intent intent;
        //Primero comprovamos si el usuario esta cargando el movil
        if (estado == BatteryManager.BATTERY_PLUGGED_AC || estado == BatteryManager.BATTERY_PLUGGED_USB) {
            intent = new Intent (getBaseContext(), ChargingErrorActivity.class);
        }
        else {
            //Luego comprobamos su estado de la bateria
            int nivelBateria = intentBateria.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            int nivelMaximo = intentBateria.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            float bateriaActual = (nivelBateria/(nivelMaximo*((float)1.0)))*100;
            if (bateriaActual <= 5) {
                intent = new Intent (getBaseContext(), MainActivity.class);
            }
            else{
                intent = new Intent (getBaseContext(), Not5Activity.class);
            }
        }
        startActivity(intent);
    }
}
