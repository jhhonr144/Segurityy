
package com.example.segurityy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

//
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    RequestQueue rq;
    JsonRequest jrq;

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    public final static int REQUEST_CODE = 10101;

    NotificationCompat.Builder notificacion;
    private EditText Correo,Pin;
    static  String Imei="sisas";

    ConexionSQLite conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        obtenerImei();



        setContentView(R.layout.activity_main);
        rq = Volley.newRequestQueue(this);
        Pin=(EditText)findViewById(R.id.Pinn);





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {


            initializeView();
        }



    }



    private void initializeView() {

        findViewById(R.id.bubble_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            iniciar();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            if (Settings.canDrawOverlays(this)) {
                initializeView();
            } else {
                Toast.makeText(this,
                        "El permiso para posicionar elementos sobre otras aplicaciones no está disponible. Cerrando aplicación",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public String obtenerImei()
    {
        String imei="";
        if(Build.VERSION.SDK_INT  < Build.VERSION_CODES.M)
        {

        }
        else
        {
            // Mayores a Android 6.0
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)   != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},  225);

            } else {
            }
        }
        imei= getIMEI();
        Imei=imei;
        return imei;
    }

    private String getIMEI() {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei =tm.getDeviceId(); // Obtiene el imei  or  "352319065579474";
        return imei;


    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No Se encontró el usuario ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        String resultados="espera";
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            resultados=(jsonObject.optString("traducion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (resultados.equals("sisas")) {
            Toast.makeText(this,"BLOQUEO ACTIVADO", Toast.LENGTH_SHORT).show();
            ////

            startService(new Intent(MainActivity.this, PowerButtonService.class));
        //
            //    finish();


            Intent i;
            i = new Intent(this, Bloqueo.class);
            startActivity(i);






            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getBaseContext(), "M_CH_ID");

            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setTicker("Hearty365")
                    .setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
                    .setContentTitle("Proteccion Activada")
                    .setContentText("El Servicio Contra Apagado No Autorizado Fue activado")
                    .setContentInfo("Info");

            NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notificationBuilder.build());






        }else{
            Toast.makeText(this, "CONTRASEÑA INCORRECTA ", Toast.LENGTH_SHORT).show();



        }


    }

    private void iniciar(){
        String url = "http://moviltraductor.000webhostapp.com/apagado/busqueda.php?imei="+Imei+"&clave="+Pin.getText().toString();
        //si retorna sisas esta la clave bien
        ///sino suale al contardor uno
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }





    private void Correo(){
        String url = "http://moviltraductor.000webhostapp.com/apagado/correo.php?imei="+Imei;
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }





}