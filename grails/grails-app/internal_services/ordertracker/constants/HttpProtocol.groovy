package ordertracker.constants

enum HttpProtocol {


    HTTP("http"),
    DELETE('DELETE'),
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    BODY("BODY"),
    METHOD("method"),
    REQUEST_URI("requestURI"),
    REMOTE_HOST("remote_host"),

    private final String value

    private HttpProtocol(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }

}