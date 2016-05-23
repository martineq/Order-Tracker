package ordertracker

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Requester
import ordertracker.services.ProductDescriptionServiceSpec
import ordertracker.tranmission.DefaultTransmission
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@Mock([User, UserType, Seller, Client, Agenda, ClientOrder, OrderDetail, Product, Brand])
@TestFor(ActualSellsStatsService)
class ActualSellsStatsServiceSpec extends Specification {

    def setup() {
        new UserLoader().load()
        new ClientLoader().load()
        new ProductLoader().load()

        new BrandLoader().load()
        // new DiscountLoader().load()
        new AgendaLoader().load()
        new SellerLoader().load()
        new ClientOrderLoader().load()
        new OrderDetailLoader().load()
        new UserTypeLoader().load()
    }

    private Requester generateRequester(String username, String token) {
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)
        requester.addProperty(Keywords.USERNAME, username)
        requester.addProperty(Keywords.TOKEN, token)
        return requester
    }

    void "test validQuery"() {
        given:
            def query = new ActualSellsStatsService()

        and:
            def user = User.findById(1)
            def martin = this.generateRequester(user.username, user.token)

        when:
            query.validate(martin)
            query.generateQuery()

        then:
            def expected = '{"status":{"result":"ok","description":"Report OK"},"data":{"clientesVisistados":1,"clientesFueraDeRuta":0,"bultosVendidos":9,"ventasDelDia":8550.0}}'
            query.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build() == expected
    }

    void "test emptyResult"() {
        given:
        def query = new ActualSellsStatsService()

        and:
        def user = User.findById(4)
        def uriel = this.generateRequester(user.username, user.token)

        when:
        query.validate(uriel)
        query.generateQuery()

        then:
        def expected = '{"status":{"result":"ok","description":"Report OK"},"data":{"clientesVisistados":0,"clientesFueraDeRuta":0,"bultosVendidos":0,"ventasDelDia":0.0}}'
        query.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build() == expected
    }

    void "test inexistentUser"() {
        given:
        def query = new ActualSellsStatsService()

        and:
        def pepe = this.generateRequester("pepe", "pepe1")

        when:
        def exceptionMessage = ""

        try {
            query.validate(pepe)
        }

        catch (QueryException e) {
            exceptionMessage = e.getMessage()
        }

        then:
            def expectedMessage = "Nombre de usuario no encontrado"
            exceptionMessage == expectedMessage

    }
}
