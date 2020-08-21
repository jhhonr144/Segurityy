package com.example.segurityy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.telephony.TelephonyManager;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.provider.Settings.ACTION_VOICE_CONTROL_BATTERY_SAVER_MODE;
import static android.provider.Settings.EXTRA_BATTERY_SAVER_MODE_ENABLED;

public class ValidarInicio extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {
    static  String Imei="sisas";
    RequestQueue rq;
    JsonRequest jrq;

    Button entrar,registrar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_inicio);



        rq = Volley.newRequestQueue(this);

        entrar=(Button)findViewById(R.id.inicio);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permiso();
        }

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerImei();

                validate();




            }
        });




    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void permiso(){
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)   != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},  225);

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
    private void validate(){
        String url = "http://moviltraductor.000webhostapp.com/apagado/validate.php?imei="+Imei;
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
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
        if (resultados.equals("true")) {
            Toast.makeText(this,"Iniciar Sesion", Toast.LENGTH_SHORT).show();
            Intent i;

            i = new Intent(ValidarInicio.this, Lock.class);
            startActivity(i);



        }else{
            Toast.makeText(this, "Por Favor Registrarse ", Toast.LENGTH_SHORT).show();
            Intent i;
            i = new Intent(ValidarInicio.this, Registroo.class);
            startActivity(i);




        }

    }


}