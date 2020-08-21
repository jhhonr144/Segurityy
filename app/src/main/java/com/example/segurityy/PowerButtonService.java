package com.example.segurityy;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.inputmethodservice.InputMethodService;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.WindowManager;

public class PowerButtonService extends Service {

    int pin=123;





    public PowerButtonService() {

    }
    @Override
    public void onCreate() {
        super.onCreate();




        LinearLayout mLinear = new LinearLayout(getApplicationContext()) {
            //home or recent button
            public void onCloseSystemDialogs(String reason) {
                if ("globalactions".equals(reason)) {

                    Toast.makeText(getApplicationContext(),"Ingresar PIN", Toast.LENGTH_SHORT).show();


                  Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS); sendBroadcast(closeDialog);

                   Intent i;

                    i = new Intent(getContext(), Bloqueo.class);
                    startActivity(i);









                }
            }

        };




        mLinear.setFocusable(true);
        View mView = LayoutInflater.from(this).inflate(R.layout.service_layout, mLinear);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);



//params
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);

      
        params.x = 0;
        params.y = 0;
        wm.addView(mView, params);
    }




    @Override

    public void onDestroy() {

        super.onDestroy();
        Toast.makeText(this, "Servicio detenido",
                Toast.LENGTH_SHORT).show();




    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}