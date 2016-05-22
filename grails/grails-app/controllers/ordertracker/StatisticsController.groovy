package ordertracker

import ordertracker.queries.QueryFacade

class StatisticsController {

    def actualSellsStats() {
        response << new QueryFacade(new ActualSellsStatsService()).solve(request)
    }
}
