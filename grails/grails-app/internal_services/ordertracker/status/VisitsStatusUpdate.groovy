package ordertracker.status

import ordertracker.Agenda
import ordertracker.constants.ClientStates
import ordertracker.constants.Constants
import ordertracker.util.CalendarDate
import ordertracker.util.logger.Log

/**
 * Created by martin on 23/05/16.
 */
class VisitsStatusUpdate extends Thread {

    private static VisitsStatusUpdate singleton = null

    public static getInstance() {
        return ( singleton != null ) ? singleton : ( singleton = new VisitsStatusUpdate() )
    }

    private boolean running

    private VisitsStatusUpdate() {
        this.running = true
    }

    private def modifyVisitsStatusBetween(long lastFiveMinutes, long now) {
        Agenda.withNewSession {
            String query = "UPDATE Agenda a SET a.state = :newState WHERE a.state = :state AND date >= :from AND date <= :to"
            long result = Agenda.executeUpdate(query, [state: ClientStates.PENDIENTE.toString(), newState: ClientStates.NO_VISITADO.toString(), from: lastFiveMinutes, to: now])
            if ( result != 0 ) Log.info("Se actualizó el estado de "+ result + " visitas a NO_VISITADO")
        }
    }

    def beforeRun() {
        Log.info("Visits Status service UP!")
    }

    def synchronizeClock() {
        Thread.sleep(new CalendarDate().remainingSecondsToNextMinute())
        Log.info("Visits Status Update sincronizado")
    }

    @Override
    void run() {
        beforeRun()
        synchronizeClock()
        while ( running ) {
            modifyVisitsStatusBetween(CalendarDate.lastFiveMinutes(), CalendarDate.currentDate())
            sleepDuringAMinute()
        }
        afterRun()
    }

    def sleepDuringAMinute() {
        try {
            Thread.sleep(Constants.A_MINUTE_IN_MS)
        }

        catch (InterruptedException e) {}
    }


    def afterRun() {
        Log.info("Visits status service DOWN!")
    }

    def terminate() {
        running = false
        singleton.interrupt()
    }

}
