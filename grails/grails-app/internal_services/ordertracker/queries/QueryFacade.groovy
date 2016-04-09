package ordertracker.queries

import org.apache.catalina.connector.RequestFacade

class QueryFacade {

    private QueryProtocol queryProtocol

    QueryFacade(AuthenticationQuery authenticationQuery) {
        this.queryProtocol = authenticationQuery
    }

    QueryFacade(Queryingly queryService) {
        this.queryProtocol = new Query(queryService)
    }

    def solve(RequestFacade request) {
        this.queryProtocol.analyse(request)
        this.queryProtocol.run()
        this.queryProtocol.buildJson()
    }
}
