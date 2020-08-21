package com.example.segurityy;

public class Utilidades {

    //Constantes campos tabla usuario
    public static final String TABLA_USUARIO="usuario";
    public static final String CAMPO_ID="Pin";
    public static final String CAMPO_NOMBRE="Correo";
    public static final String CAMPO_IMEI="Imei";


    public static final String CREAR_TABLA_USUARIO="CREATE TABLE " +
            ""+TABLA_USUARIO+" ("+CAMPO_ID+" " +
            "INTEGER, "+CAMPO_NOMBRE+" TEXT ,"+CAMPO_IMEI+" TEXT    )";


}

