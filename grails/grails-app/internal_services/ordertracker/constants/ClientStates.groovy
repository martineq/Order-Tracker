package ordertracker.constants

enum ClientStates {

    VISITADO("Visitado"),
    PENDIENTE("Pendiente"),
    NO_VISITADO("No visitado")

    private final String value

    private ClientStates(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }
}