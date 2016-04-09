package ordertracker

import grails.plugins.rest.client.RestBuilder
import spock.lang.Specification

class AuthenticationControllerSpec extends Specification {

    void "test invalidMethod"() {
        given:
        def rest = new RestBuilder()

        and:
        Closure requestHeaders = {
            header("username","")
        }

        when:
        def response = rest.put("http://localhost:8080/authentication/authenticate", requestHeaders )

        then:
        response.body ==  '{"status":{"result":"error","description":"Invalid HTTP request method: must be GET"}}'
    }

    void "test invalidHeader"() {
        given:
        def rest = new RestBuilder()

        and:
        Closure requestHeaders = {
            header("username","")
        }

        when:
        def response = rest.get("http://localhost:8080/authentication/authenticate", requestHeaders )

        then:
        response.body ==  '{"status":{"result":"error","description":"Invalid request"}}'
    }

    void "test invalidUser"() {
        given:
        def rest = new RestBuilder()

        and:
        Closure requestHeaders = {
            header("username","")
            header("password","")
        }

        when:
        def response = rest.get("http://localhost:8080/authentication/authenticate", requestHeaders )

        then:
        response.body ==  '{"status":{"result":"error","description":"Authentication failed"}}'
    }

    void "test validUser"() {
        given:
            def rest = new RestBuilder()

        and:
            Closure requestHeaders = {
                header("username","martin")
                header("password","85780")
            }

        when:
            def response = rest.get("http://localhost:8080/authentication/authenticate", requestHeaders )

        then:
            response.body == '{"status":{"result":"ok","description":"Authenticated username"},"data":{"token":"token1"}}'
    }

}
