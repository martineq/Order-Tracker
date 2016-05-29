package ordertracker.internalServices.dtos

import org.grails.web.json.JSONObject

interface DTO {

    boolean parse(JSONObject jsonObject)
}