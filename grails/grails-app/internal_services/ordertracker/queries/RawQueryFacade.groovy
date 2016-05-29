package ordertracker.queries

class RawQueryFacade extends QueryFacade {

    RawQueryFacade(Queryingly queryService) {
        super(new SimpleQuery(queryService))
    }
}
