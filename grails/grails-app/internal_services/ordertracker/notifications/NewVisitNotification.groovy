package ordertracker.notifications

import ordertracker.Agenda
import ordertracker.Client
import ordertracker.util.CalendarDate

class NewVisitNotification extends Notification {

    private Client client
    private Agenda agenda

    NewVisitNotification(Agenda agenda){
        this.agenda = agenda
        this.client = Client.findById(agenda.client_id)
    }

    def defineTitle() {
        return "Nueva Visita"
    }

    def defineBody() {
        return client.name + ' - ' + CalendarDate.getWeekDayInSpanish(agenda.day) + ' ' + agenda.time
    }
}
