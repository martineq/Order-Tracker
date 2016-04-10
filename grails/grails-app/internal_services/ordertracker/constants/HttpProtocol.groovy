package ordertracker.constants

enum HttpProtocol {


    HTTP("http"),
    GET("GET"),
    PUT("PUT"),
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