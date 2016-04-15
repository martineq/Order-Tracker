package ordertracker.util.logger

import org.apache.catalina.connector.RequestFacade

class InfoFormatter {

    private String date

    InfoFormatter() {
        this.date = 0
    }

    def format(RequestFacade request, String type) {
        String username = request.getHeader('username')
        if ( username == null ) username = 'anonymous'

        String ip = request.remoteAddr
        String action = request.requestURI
        String method = request.method

        this.format(username + " requested " + action + " access from ip " + ip + " using " + method + " method ", type)
    }

    String format(String message, String type) {
        Date date = new Date()

        if (this.date.compareTo(date.getDateString()) != 0) {
            this.date = date.getDateString()
            throw new DateChangedException(this.date)
        }

        return "#"+type+" ["+date.getTimeString()+"]: "+message
    }
}
