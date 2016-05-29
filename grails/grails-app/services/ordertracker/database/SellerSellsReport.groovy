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

    private long seller_id

    private long visitedClients
    private long outOfRouteVisitedClients
    private long totalSoldUnits
    private double todayGrossSales

    private long todayFirstSecond
    private long todayLastSecond

    SellerSellsReport(long seller_id) {
        this.seller_id = seller_id
        visitedClients = 0
        outOfRouteVisitedClients = 0
        totalSoldUnits = 0
        todayGrossSales = 0.0

        todayFirstSecond = CalendarDate.todayFirstSecond()
        todayLastSecond = CalendarDate.todayLastSecond()
    }

    def caclulateVisitedClients() {
        def select = "SELECT COUNT(*) from Agenda a "
        def where = "WHERE a.seller_id = :seller AND a.state = :currentState AND a.date >= :firstSecond AND a.date <= :lastSecond AND a.visitedDate >= :firstSecond AND a.visitedDate <= :lastSecond"

        try {
            visitedClients = Agenda.executeQuery(select + where, [seller: seller_id, currentState: ClientStates.VISITADO.toString(), firstSecond: todayFirstSecond, lastSecond: todayLastSecond]).get(0)
        }

        catch ( Exception e){
            visitedClients = 0
        }

        return visitedClients
    }

    def caclulateOutOfRouteVisitedClients() {
        def select = "SELECT COUNT(*) from Agenda a "
        def where = "WHERE a.seller_id = :seller AND a.state = :currentState AND ( a.date < :firstSecond OR a.date > :lastSecond) AND a.visitedDate >= :firstSecond AND a.visitedDate <= :lastSecond"

        try {
            outOfRouteVisitedClients = Agenda.executeQuery(select + where, [seller: seller_id, currentState: ClientStates.VISITADO.toString(), firstSecond: todayFirstSecond, lastSecond: todayLastSecond]).get(0)
        }

        catch (Exception e) {
            outOfRouteVisitedClients = 0
        }

        return outOfRouteVisitedClients
    }

    def calculateTotalSoldUnits() {
        def select = "SELECT SUM(d.requested_items) FROM ClientOrder o , OrderDetail d "
        def where = " WHERE o.seller_id = :seller AND o.id = d.order_id AND o.date >= :firstSecond AND o.date <= :lastSecond AND o.state != :orderState"

        try {
            totalSoldUnits = ClientOrder.executeQuery(select + where, [seller: (int) seller_id, firstSecond: todayFirstSecond, lastSecond: todayLastSecond, orderState: OrderStates.CANCELADO.toString()]).get(0)
        }

        catch (Exception e) {
            totalSoldUnits = 0
        }

        return totalSoldUnits
    }

    def calculateGrossSales() {
        def select = "SELECT SUM(o.total_price) FROM ClientOrder o "
        def where = "WHERE o.seller_id = :seller AND o.date >= :firstSecond AND o.date <= :lastSecond AND o.state != :orderState"

        try {
            todayGrossSales = ClientOrder.executeQuery(select + where, [seller: (int) seller_id, firstSecond: todayFirstSecond, lastSecond: todayLastSecond, orderState: OrderStates.CANCELADO.toString()]).get(0)
        }

        catch (Exception e) {
            todayGrossSales = 0.0
        }

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
