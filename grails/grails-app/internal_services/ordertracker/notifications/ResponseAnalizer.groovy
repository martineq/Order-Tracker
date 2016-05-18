package ordertracker.notifications

import grails.converters.JSON
import grails.plugins.rest.client.RestResponse
import ordertracker.constants.Keywords
import ordertracker.util.logger.Log
import org.grails.web.json.JSONObject

/**
 * Created by martin on 18/05/16.
 */
class ResponseAnalizer {

    private RestResponse response

    ResponseAnalizer(RestResponse response) {
        this.response = response
    }

    public def analizeResponse() {
        boolean result = false

        try {
            result = this.analizeBody(response.getText())
        }

        catch (RuntimeException e) {
            Log.info("Error analizando respuesta: "+ e.getMessage())
        }

        finally {
            return result
        }
    }

    public def analizeBody(String body) {
        JSONObject jsonObject = JSON.parse(body)

        if (jsonObject.size() == 0) {
            Log.info("Rest service error")
            return false
        }

        else return this.analizeMessageSuccess(jsonObject)
    }

    public def analizeMessageSuccess(JSONObject jsonObject) {
        if ( jsonObject.getString(Keywords.SUCCESS.toString()) == '0' ) {

            def results = jsonObject.getString(Keywords.RESULTS.toString())
            JSONObject jsonError = JSON.parse(results)
            Log.info("No se pudo enviar el mensaje: " + jsonError.getString(Keywords.ERROR.toString()))
            return false

        } else {
            Log.info("Mensaje enviado")
            return true
        }
    }
}
