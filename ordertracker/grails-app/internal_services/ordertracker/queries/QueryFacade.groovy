package ordertracker.queries

import org.apache.catalina.connector.RequestFacade

class QueryFacade {

    private QueryProtocol queryProtocol

    QueryFacade(AuthenticationQuery authenticationQuery) {
        this.queryProtocol = authenticationQuery
    }

    def solve(RequestFacade request) {
        this.queryProtocol.analyse(request)
        this.queryProtocol.run()
        this.queryProtocol.buildJson()
    }
}
