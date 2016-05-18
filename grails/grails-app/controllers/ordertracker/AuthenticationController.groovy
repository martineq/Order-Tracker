package ordertracker

import grails.plugins.rest.client.RestBuilder
import ordertracker.notifications.PushMessageHandler
import ordertracker.queries.AuthenticationQuery
import ordertracker.queries.QueryFacade

class AuthenticationController {

    def index() {
        def renderer = new Renderer()
        render renderer.message("Métodos disponibles<br>")
        render renderer.link("authenticate", "verifica si el usuario es válido")
    }

    def authenticate() {
        response << new QueryFacade(new AuthenticationQuery()).solve(request)
    }

    def send() {

        def resp = new RestBuilder().post('https://gcm-http.googleapis.com/gcm/send'){
            header 'Content-Type', 'application/json'
            header 'Authorization', 'key=AIzaSyBdpUd3L643VWf12vzRg2uGbb_2-scKOCQ'
            json '{ "data": {"score": "5x1","time": "15:10" },"to" : "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1..." }'
        }

        response << resp.body
    }

    def gcm() {
        println("GCM********************************")

        try {
            def gcm = new PushMessageHandler("AIzaSyBdpUd3L643VWf12vzRg2uGbb_2-scKOCQ")
            def json = '{ "data": {"score": "5x1","time": "15:10" },"to" : "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1..." }'
            def resp = gcm.sendMessage(json)
            String body = resp.getBody()
            gcm.analize(body)

            response << resp.body

        } catch (NullPointerException e  ){
            response << null
        }
    }
}
