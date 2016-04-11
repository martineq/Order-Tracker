package ordertracker.constants

enum ServerDetails {

    SERVER_IP_FILE('grails-app/conf/serverPublicIPAddress'),
    SERVER_PORT_FILE('grails-app/conf/serverPublicPort'),
    SERVER_DEFAULT_IP('localhost'),
    SERVER_DEFAULT_PORT('8080'),

    private final String value

    private ServerDetails(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }
}