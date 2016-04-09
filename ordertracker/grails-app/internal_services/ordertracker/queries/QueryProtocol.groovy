package ordertracker.queries

import org.apache.catalina.connector.RequestFacade

interface QueryProtocol {

    QueryProtocol analyse(RequestFacade request)

    QueryProtocol run()

    def buildJson()

}
