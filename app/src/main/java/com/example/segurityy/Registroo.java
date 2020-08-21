package com.example.segurityy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

import org.json.JSONObject;

public class Registroo extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {
    static  String Imei="sisas";
    RequestQueue rq;
    JsonRequest jrq;
    private EditText Correo,Pin;
    Button entrar,registrar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroo);
        obtenerImei();

        rq = Volley.newRequestQueue(this);
        Correo=(EditText)findViewById(R.id.Correoo);

        Pin=(EditText)findViewById(R.id.pin);

        registrar=(Button)findViewById(R.id.registrarr);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              registrarwill();


            }
        });







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

        Toast.makeText(this, "Registrado ", Toast.LENGTH_SHORT).show();
        Intent i;
        i = new Intent(Registroo.this, MainActivity.class);
        startActivity(i);

    }
    private void registrarwill(){
        String imeix=Imei;
        String correox=Correo.getText().toString();
        String pinx=Pin.getText().toString();
        String url = "http://moviltraductor.000webhostapp.com/apagado/registrar.php?correo="
                +correox
                +"&clave="
                +pinx
                +"&nombre="
                +imeix;
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
}
