package com.example.uriel.ordertracker.App.Model.Dto;

/**
 * Created by Uriel on 29-Mar-16.
 */
/**
 * DTO Base del cual extienden todos los de la aplicación. Contiene atributos comunes como status (OK, ERROR) y code (código de error en caso de resultado erróneo)
 */
public class BaseDTO {

    private StatusDTO status;
    private int code;

    public StatusDTO getStatus() {
        return status;
    }

    public void setResult(StatusDTO status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
