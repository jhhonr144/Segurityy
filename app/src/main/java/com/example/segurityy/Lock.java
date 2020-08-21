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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Lock extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {
    static  String Imei="sisas";
    RequestQueue rq;
    JsonRequest jrq;
    private EditText Correo,Pin;
    Button entrar,registrar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        obtenerImei();

        rq = Volley.newRequestQueue(this);


        Pin=(EditText)findViewById(R.id.pin);
        entrar=(Button)findViewById(R.id.entrar);





        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar();

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
            Toast.makeText(this,"Iniciando ", Toast.LENGTH_SHORT).show();
            Intent i;

            i = new Intent(Lock.this, MainActivity.class);
            startActivity(i);


        }else{
            Toast.makeText(this, "PIN INCORRECTO ", Toast.LENGTH_SHORT).show();




        }

    }
    private void iniciar(){
        String url = "http://moviltraductor.000webhostapp.com/apagado/busqueda.php?imei="+Imei+"&clave="+Pin.getText().toString();
        //si retorna sisas esta la clave bien
        ///sino suale al contardor uno
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

}
