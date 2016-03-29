package com.example.uriel.ordertracker.App.Model.Dto;

/**
 * Created by Uriel on 29-Mar-16.
 */
/**
 * DTO Base del cual extienden todos los de la aplicación. Contiene atributos comunes como result (OK, ERROR) y code (código de error en caso de resultado erróneo)
 */
public class BaseDTO {

    private String result;
    private int code;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
