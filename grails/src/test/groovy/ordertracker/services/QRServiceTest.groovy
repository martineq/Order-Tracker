package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.AvailableProductsService
import ordertracker.Brand
import ordertracker.BrandLoader
import ordertracker.Client
import ordertracker.ClientLoader
import ordertracker.Product
import ordertracker.ProductLoader
import ordertracker.QRService
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.constants.ClientStates
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Requester
import ordertracker.tranmission.DefaultTransmission
import ordertracker.tranmission.TransmissionMedium
import org.apache.tools.ant.taskdefs.condition.Http
import spock.lang.Specification

@Mock([User, Product, Brand, Client])
@TestFor(ordertracker.QRService)
class QRServiceTest extends Specification {

    def setup() {
        new UserLoader().load()
        new ClientLoader().load()

        Client.findById(1).setQrCode()
    }

    private Requester generateMartinRequester(Enum httpMethod) {
        String qrCode = '"1-a@a.com"'
        String qrJson = '{"'+Keywords.QR.toString()+'":'+qrCode+'}'

        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, httpMethod)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        requester.addProperty(Keywords.CLIENT_ID, 1)
        requester.addProperty(HttpProtocol.BODY, qrJson)
        return requester
    }

    void "test invalidMethodRequest"() {
        given:
                def requester = new Requester()
                requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)

        when:
                String message = "";
                try {
                    new QRService().validate(requester)
                }
                catch( QueryException e ) {
                    message = e.getMessage();
                }

        then:
                message == "Invalid HTTP request method: must be POST"
    }

    void "test requestWithoutBody"() {
        given:
        def requester = new Requester()
            requester.addProperty(Keywords.USERNAME, 'martin')
            requester.addProperty(Keywords.TOKEN, 'token1')
            requester.addProperty(Keywords.CLIENT_ID, 1)
            requester.addProperty(HttpProtocol.METHOD, HttpProtocol.POST)

        when:
            String message = "";
            try {
                new QRService().validate(requester)
            }
            catch( QueryException e ) {
                message = e.getMessage();
            }

        then:
            message == "Invalid request"
    }

    void "test emptyBodyRequest"() {
        given:
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.POST)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        requester.addProperty(Keywords.CLIENT_ID, 1)
        requester.addProperty(HttpProtocol.BODY, "")

        when:
            String message = "";
            try {
                new QRService().validate(requester)
            }
            catch( QueryException e ) {
                message = e.getMessage();
            }

        then:
            message == "No se recibió el código QR en el cuerpo del HTTP request"
    }

    void "test validRequest"() {
        given:
            def requester = this.generateMartinRequester(HttpProtocol.POST)

        when:
            QRService qrService = new QRService()

        then:
            qrService.validate(requester) == true
    }

    void "test validResponse"() {
        given:
            def qrRequester = this.generateMartinRequester(HttpProtocol.POST)
            def productRequester = this.generateMartinRequester(HttpProtocol.GET)

        and:
            new ProductLoader().load()
            new BrandLoader().load()

        and:
            def qrService = new QRService()
            def productsService = new AvailableProductsService()

        when:

            qrService.validate(qrRequester)
            qrService.generateQuery()

            productsService.validate(productRequester)
            productsService.generateQuery()

        then:
            def qrServiceResponse = qrService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()
            def productsServiceResponse = productsService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()
            qrServiceResponse == productsServiceResponse
    }

    void "test invalidResponse"() {
        given:
            def qrRequester = this.generateMartinRequester(HttpProtocol.POST)

        and:
            def qrService = new QRService()

        when:
            qrService.validate(qrRequester)

        then:
        def qrServiceResponse = qrService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()
        qrServiceResponse == '{\"status\":{\"result\":\"error\",\"description\":\"Código QR Inválido\"}}'
    }

    void "test validQRCode"() {
        given:
            def qrRequester = this.generateMartinRequester(HttpProtocol.POST)

        and:
            def qrService = new QRService()

        when:
            qrService.validate(qrRequester)
            def result = qrService.generateQuery()

        then:
            result == true
    }

    void "test clientAlreadyVisited"() {
        given:
            def qrRequester = this.generateMartinRequester(HttpProtocol.POST)

        and:
            Client.findById(1).setState(ClientStates.VISITADO.toString())

        and:
            def qrService = new QRService()

        when:
            qrService.validate(qrRequester)
            qrService.generateQuery()

        then:
        def qrServiceResponse = qrService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()
        qrServiceResponse == '{\"status\":{\"result\":\"error\",\"description\":\"El cliente ya se ha VISITADO\"}}'
    }

    void "test invalidClientQRCode"() {
        given:
        String body = '{"qr":"100000-a@a.com"}'
        def qrRequester = this.generateMartinRequester(HttpProtocol.POST)
        qrRequester.addProperty(HttpProtocol.BODY.toString(), body)

        and:
        def qrService = new QRService()

        when:
        qrService.validate(qrRequester)
        def result = qrService.generateQuery()

        then:
        result == false
    }

    void "test invalidMailInCorrectClientQRCode"() {
        given:
        String body = '{"qr":"1-a@ab.com"}'
        def qrRequester = this.generateMartinRequester(HttpProtocol.POST)
        qrRequester.addProperty(HttpProtocol.BODY.toString(), body)

        and:
        def qrService = new QRService()

        when:
        qrService.validate(qrRequester)
        def result = qrService.generateQuery()

        then:
        result == false
    }
}
