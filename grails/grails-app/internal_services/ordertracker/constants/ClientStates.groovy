package ordertracker.constants

enum ClientStates {

    VISITADO("VISITADO"),
    PENDIENTE("PENDIENTE"),
    NO_VISITADO("NO_VISITADO")

    private final String value

    private ClientStates(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }
}