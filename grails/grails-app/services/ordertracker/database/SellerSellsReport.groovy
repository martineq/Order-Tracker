package ordertracker.database

import ordertracker.Agenda
import ordertracker.ClientOrder
import ordertracker.constants.ClientStates
import ordertracker.constants.OrderStates
import ordertracker.util.CalendarDate

/**
 * Created by martin on 22/05/16.
 */
class SellerSellsReport {

    private long visitedClients
    private long outOfRouteVisitedClients
    private long totalSoldUnits
    private double todayGrossSales

    private long todayFirstSecond
    private long todayLastSecond

    SellerSellsReport() {
        visitedClients = 0
        outOfRouteVisitedClients = 0
        totalSoldUnits = 0
        todayGrossSales = 0.0

        todayFirstSecond = CalendarDate.todayFirstSecond()
        todayLastSecond = CalendarDate.todayLastSecond()
    }

    def caclulateVisitedClients() {
        def select = "SELECT COUNT(*) from Agenda a "
        def where = "WHERE a.state = :currentState AND a.date >= :firstSecond AND a.date <= :lastSecond AND a.visitedDate >= :firstSecond AND a.visitedDate <= :lastSecond"

        visitedClients = Agenda.executeQuery( select + where, [currentState: ClientStates.VISITADO.toString(), firstSecond: todayFirstSecond, lastSecond: todayLastSecond] ).get(0)

        println("visitedClients: "+visitedClients)

        return visitedClients
    }

    def caclulateOutOfRouteVisitedClients() {
        def select = "SELECT COUNT(*) from Agenda a "
        def where = "WHERE a.state = :currentState AND ( a.date < :firstSecond OR a.date > :lastSecond) AND a.visitedDate >= :firstSecond AND a.visitedDate <= :lastSecond"

        outOfRouteVisitedClients = Agenda.executeQuery( select + where, [currentState: ClientStates.VISITADO.toString(), firstSecond: todayFirstSecond, lastSecond: todayLastSecond] ).get(0)

        println("outOfRouteVisitedClients: " +outOfRouteVisitedClients)

        return outOfRouteVisitedClients
    }

    def calculateTotalSoldUnits() {
        def select = "SELECT SUM(d.requested_items) FROM ClientOrder o , OrderDetail d "
        def where = " WHERE o.id = d.order_id AND o.date >= :firstSecond AND o.date <= :lastSecond AND o.state != :orderState"

        def totalSoldUnits = ClientOrder.executeQuery( select + where , [firstSecond: todayFirstSecond, lastSecond: todayLastSecond, orderState: OrderStates.CANCELADO.toString()] ).get(0)

        println("totalSoldUnits: " + totalSoldUnits)

        return totalSoldUnits
    }

    def calculateGrossSales() {
        def select = "SELECT SUM(o.total_price) FROM ClientOrder o "
        def where = "WHERE o.date >= :firstSecond AND o.date <= :lastSecond AND o.state != :orderState"

        todayGrossSales = ClientOrder.executeQuery( select + where, [firstSecond: todayFirstSecond, lastSecond: todayLastSecond, orderState: OrderStates.CANCELADO.toString()] ).get(0)

        println("grossSales: "+todayGrossSales)

        return todayGrossSales
    }

    long getVisitedClients() {
        return visitedClients
    }

    long getOutOfRouteVisitedClients() {
        return outOfRouteVisitedClients
    }

    long getTotalSoldUnits() {
        return totalSoldUnits
    }

    double getTodayGrossSales() {
        return todayGrossSales
    }
}
