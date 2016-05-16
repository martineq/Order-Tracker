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

    @Override
    NotificationType defineNotificationType() {
        return NotificationType.AGENDA
    }

    def getTitle() {
        return "Nueva Visita"
    }

    def getBody() {
        return client.name + ' - ' + CalendarDate.getWeekDayInSpanish(agenda.day) + ' ' + agenda.time
    }
}
