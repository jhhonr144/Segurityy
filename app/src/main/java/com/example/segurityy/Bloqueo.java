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

import static android.provider.Settings.ACTION_VOICE_CONTROL_BATTERY_SAVER_MODE;
import static android.provider.Settings.EXTRA_BATTERY_SAVER_MODE_ENABLED;

public class  Bloqueo extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {
    static  String Imei="sisas";
    RequestQueue rq;
    JsonRequest jrq;
    private EditText Correo,Pin;
    Button entrar,registrar ;
    int conteo = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo);

        obtenerImei();

        rq = Volley.newRequestQueue(this);


        Pin=(EditText)findViewById(R.id.Pinn);
        entrar=(Button)findViewById(R.id.bubble_button);





        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar();
                conteo = conteo +1;

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
            Toast.makeText(this,"PROTECCION DESACTIVADA ", Toast.LENGTH_SHORT).show();

            stopService(new Intent(Bloqueo.this, PowerButtonService.class));
            finish();
            View mView = LayoutInflater.from(getBaseContext()).inflate(R.layout.service_layout,null);
            System.exit(0);
            stopService(new Intent(String.valueOf(mView)));





        }else{
            Toast.makeText(this, "PIN INCORRECTO "+conteo, Toast.LENGTH_SHORT).show();

            if (conteo == 3){



                Intent i;
                i = new Intent(Bloqueo.this, EnviandoCorreo.class);
                startActivity(i);

            }




        }

    }
    private void iniciar(){
        String url = "http://moviltraductor.000webhostapp.com/apagado/busqueda.php?imei="+Imei+"&clave="+Pin.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

}
