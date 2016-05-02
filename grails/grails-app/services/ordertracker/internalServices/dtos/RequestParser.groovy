package ordertracker.internalServices.dtos

import grails.converters.JSON
import ordertracker.queries.QueryException
import org.grails.web.converters.exceptions.ConverterException
import org.grails.web.json.JSONException

import javax.validation.constraints.Null

class RequestParser {

    private boolean validJson

    RequestParser() {
        this.validJson = false
    }

    public boolean isValid() {
        return this.validJson
    }

    public DTO parse(String message, DTO dto) {
        try {
            this.validJson = dto.parse(JSON.parse(message));
            return dto
        }

        catch( NullPointerException | ConverterException g ) {
            throw new QueryException("No se recibió un objeto json válido")
        }
    }
}
