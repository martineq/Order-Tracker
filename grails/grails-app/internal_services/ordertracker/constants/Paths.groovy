package ordertracker.constants

enum Paths {

    BRANDS("/home/martin/Escritorio/ordertracker/images/brands/"),
    PRODUCTS("/home/martin/Escritorio/ordertracker/images/products/"),

    private final String value

    private Paths(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }
}