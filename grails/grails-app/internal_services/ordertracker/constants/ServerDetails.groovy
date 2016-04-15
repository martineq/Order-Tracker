package ordertracker.constants

enum ServerDetails {

    SERVER_TMP_DIR('tmp/'),
    SERVER_ERROR_IMAGE_DIR('images/errors/'),
    SERVER_PRODUCTS_IMAGE_DIR('images/products/'),
    SERVER_LOGS_PATH('logs/'),
    SERVER_INFO_LOG_FILE_NAME('info.log'),
    SERVER_WARN_LOG_FILE_NAME('warn.log'),
    SERVER_SECW_LOG_FILE_NAME('security.log'),
    SERVER_IP_FILE('grails-app/conf/serverPublicIPAddress'),
    SERVER_PORT_FILE('grails-app/conf/serverPublicPort'),
    SERVER_DEFAULT_IP('localhost'),
    SERVER_DEFAULT_PORT('8080'),
    SERVER_LOG_INFO('INFO'),
    SERVER_LOG_WARN('WARN'),
    SERVER_LOG_SECW('SECW'),

    private final String value

    private ServerDetails(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }
}