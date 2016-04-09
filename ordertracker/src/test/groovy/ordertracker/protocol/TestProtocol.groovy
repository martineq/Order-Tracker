package ordertracker.protocol

import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import spock.lang.Specification

class TestProtocol extends Specification {

    void "test responseName"() {
        given:
            def status = new Status(Result.OK)
            def data = new Data(new JsonObjectBuilder().addJsonableItem(new JsonPropertyFactory("name","martin")))
            def jsonProtocol = new ProtocolJsonBuilder(status, data)

        when:
            def json = jsonProtocol.build()

        then:
            json == '{"status":{"result":"ok","description":""},"data":{"name":"martin"}}'
    }

    void "test okStatusReponse"() {
        given:
            def status = new Status(Result.OK)
            def jsonProtocol = new ProtocolJsonBuilder(status)

        when:
            def json = jsonProtocol.build()

        then:
            json == '{"status":{"result":"ok","description":""}}'
    }

    void "test twoJsonObjects"() {

        given:
        def status = new Status(Result.OK)
        def jsonObject1 = new JsonObjectBuilder().addJsonableItem(new JsonPropertyFactory("name","martin"))
        def jsonObject2 = new JsonObjectBuilder().addJsonableItem(new JsonPropertyFactory("username","martin"))
        def data = new Data(new JsonObjectBuilder().addJsonableItem(jsonObject1).addJsonableItem(jsonObject2));
        def jsonProtocol = new ProtocolJsonBuilder(status, data)

        when:
            def json = jsonProtocol.build()

        then:
            json == '{"status":{"result":"ok","description":""},"data":[{"name":"martin"},{"username":"martin"}]}'

    }
}
