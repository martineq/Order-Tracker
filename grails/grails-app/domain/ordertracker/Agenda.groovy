package ordertracker

import ordertracker.constants.ClientStates
import ordertracker.constants.Keywords
import ordertracker.notifications.ModifiedVisitNotification
import ordertracker.notifications.NewVisitNotification
import ordertracker.util.CalendarDate

import java.text.SimpleDateFormat

class Agenda {

    static transients = ['dateModified']
    boolean	dateModified

    static constraints = {
    }

    long    seller_id
    long    client_id
    long    date
    long    visitedDate
    String  state

    int day
    String time

    def beforeUpdate() {
        dateModified = this.isDirty('date')
    }

    def afterInsert() {
        new NewVisitNotification(this).addNotification(this.seller_id)
        GCMConnectorService.getInstance().push()
    }

    def afterUpdate() {
        if ( dateModified == true ) {
            dateModified = false
            new ModifiedVisitNotification(this).addNotification(this.seller_id)
            GCMConnectorService.getInstance().push()
        }
    }

    Agenda(long seller_id, long client_id, long date) {
        def calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))

        this.dateModified = false
        this.seller_id = seller_id
        this.client_id = client_id
        this.date = date
        this.visitedDate = 0
        this.state = this.getCurrentState(date, calendar)

        calendar.setFirstDayOfWeek(Calendar.SUNDAY)
        calendar.setTimeInMillis(date)

        this.day = calendar.get(Calendar.DAY_OF_WEEK)
        String time = calendar.getTime().getTimeString()
        this.time = time.substring(0, time.lastIndexOf(':'))
    }

    private String getCurrentState(long date, Calendar calendar) {
        def state = ClientStates.PENDIENTE

        if ( date < calendar.getTimeInMillis() ) {
            state = ((Math.random() * 2.00) < 1) ? ClientStates.VISITADO : ClientStates.NO_VISITADO

            if ( state == ClientStates.VISITADO.toString() )
                this.visitedDate = ((Math.random() * 2.00) < 1.5 ) ? this.date : calendar.getTimeInMillis()
        }

        return state.toString()
    }
}
