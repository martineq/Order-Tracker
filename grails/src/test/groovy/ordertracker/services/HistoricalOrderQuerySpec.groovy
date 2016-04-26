package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.ClientOrder
import ordertracker.ClientOrderLoader
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.UserType
import ordertracker.UserTypeLoader
import ordertracker.constants.Keywords
import ordertracker.database.HistoricalOrderQuery
import ordertracker.util.CalendarDate
import spock.lang.Specification

@Mock( [User, UserType, ClientOrder])
class HistoricalOrderQuerySpec extends Specification {

    def setup() {
        new UserLoader().load()
        new UserTypeLoader().load()
        new ClientOrderLoader().load()
    }

    void "test validTest"() {
        given:
            def startingDate = CalendarDate.fromCurrentDate(-4)
            def endingDate = CalendarDate.fromCurrentDate(+3)

        and:
            def query = new HistoricalOrderQuery(startingDate, endingDate)

        when:
            def orders = query.search('martin')

        then:
            orders.size() == 7
    }

    void "test validTestUriel"() {
        given:
            def startingDate = CalendarDate.fromCurrentDate(-4)
            def endingDate = CalendarDate.fromCurrentDate(+3)

        and:
            def query = new HistoricalOrderQuery(startingDate, endingDate)

        when:
            def orders = query.search('uriel')

        then:
            orders.size() == 7
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
