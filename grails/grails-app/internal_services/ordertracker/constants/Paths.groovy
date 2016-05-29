package ordertracker.constants

enum Paths {

    BRANDS("/home/poly/tp7547/grails/images/brands/"),
    PRODUCTS("/home/poly/tp7547/grails/images/products/"),

    private final String value

    private Paths(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }
}