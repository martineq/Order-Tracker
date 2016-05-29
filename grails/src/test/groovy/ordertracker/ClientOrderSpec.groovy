package ordertracker

import grails.test.mixin.TestFor
import grails.validation.exceptions.ConstraintException
import ordertracker.constants.ClientStates
import spock.lang.Specification

@TestFor(ClientOrder)
class ClientOrderSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test noExceptionWithWrongData"() {
        given:
            def clientOrder = null
            def exceptionType = ""

        when:
            try{
                clientOrder = new ClientOrder(seller_id: 0, client_id: 1, state: ClientStates.VISITADO.toString(), date:0, total_price:1.0)
            }

            catch ( Exception e ) {
                exceptionType = e.getClass().getSimpleName()
            }

        then:
            clientOrder != null
    }

    void "test wrongSelledID"() {
        given:
            def clientOrder = new ClientOrder(seller_id: 0, client_id: 1, state: ClientStates.VISITADO.toString(), date:0, total_price:1.0)

        expect:
            clientOrder.validate() == false
    }

    void "test negativeTotalPrice"() {
        given:
            def clientOrder = new ClientOrder(seller_id: 1, client_id: 1, state: ClientStates.VISITADO.toString(), date:0, total_price:-1.0)

        expect:
            clientOrder.validate() == false
    }


    void "test negativeClient_ID"() {
        given:
        def clientOrder = new ClientOrder(seller_id: 1, client_id: -1, state: ClientStates.VISITADO.toString(), date:0, total_price:1.0)

        expect:
            clientOrder.validate() == false
    }

    void "test wrongState"() {
        given:
            def clientOrder = new ClientOrder(seller_id: 0, client_id: 1, state: "pepe", date:0, total_price:1.0)

        expect:
            clientOrder.validate() == false
    }

}
