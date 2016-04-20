package ordertracker.constants

enum HttpProtocol {


    HTTP("http"),
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    BODY("BODY"),
    METHOD("method"),
    REQUEST_URI("requestURI"),

    private final String value

    private HttpProtocol(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }

}