package com.example.segurityy;

import java.io.Serializable;

public class Usuarios implements Serializable {
    private Integer Pin;
    private  String Correo;
    private  String Imei;

    public Usuarios(Integer pin, String correo, String imei) {
        Pin = pin;
        Correo = correo;
        Imei = imei;
    }

    public Integer getPin() {
        return Pin;
    }

    public void setPin(Integer pin) {
        Pin = pin;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }
}
