package ordertracker.constants

enum OrderStates {

    DESPACHADO("DESPACHADO"),
    SOLICITADO("SOLICITADO"),
    CANCELADO("CANCELADO")

    private final String value

    private OrderStates(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }
}