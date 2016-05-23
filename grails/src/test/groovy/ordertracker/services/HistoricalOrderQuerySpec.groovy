package ordertracker.services

import grails.test.mixin.Mock
import ordertracker.Client
import ordertracker.ClientLoader
import ordertracker.ClientOrder
import ordertracker.ClientOrderLoader
import ordertracker.Seller
import ordertracker.SellerLoader
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.UserType
import ordertracker.UserTypeLoader
import ordertracker.database.HistoricalOrderQuery
import ordertracker.util.CalendarDate
import spock.lang.Specification

@Mock( [Seller, Client, User, UserType, ClientOrder])
class HistoricalOrderQuerySpec extends Specification {

    def setup() {
        new SellerLoader().load()
        new ClientLoader().load()
        new UserLoader().load()
        new UserTypeLoader().load()
        new ClientOrderLoader().load()
    }

    void "test validTest"() {
        given:
            def startingDate = CalendarDate.fromCurrentDate(-7)
            def endingDate = CalendarDate.fromCurrentDate(+7)

        and:
            def query = new HistoricalOrderQuery(startingDate, endingDate)

        when:
            def orders = query.search('martin')

        then:
            orders.size() == 3
    }

    void "test validTestUriel"() {
        given:
            def startingDate = CalendarDate.fromCurrentDate(-7)
            def endingDate = CalendarDate.fromCurrentDate(+7)

        and:
            def query = new HistoricalOrderQuery(startingDate, endingDate)

        when:
            def orders = query.search('uriel')

        then:
            orders.size() == 0
    }


    void "test validEmptyTest"() {
        given:
            def startingDate = CalendarDate.fromCurrentDate(-20)
            def endingDate = CalendarDate.fromCurrentDate(-10)

        and:
            def query = new HistoricalOrderQuery(startingDate, endingDate)

        when:
            def orders = query.search('martin')

        then:
            orders.size() == 0
    }
}
