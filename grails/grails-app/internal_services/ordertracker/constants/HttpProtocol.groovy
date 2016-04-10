package ordertracker.constants

enum HttpProtocol {

    GET("GET"),
    PUT("PUT"),
    METHOD("method"),

    private final String value

    private HttpProtocol(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }

}