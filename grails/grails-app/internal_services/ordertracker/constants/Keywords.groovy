package ordertracker.constants

enum Keywords {

    USERNAME("username"),
    PASSWORD("password"),
    TOKEN("token"),

    BRAND_ID("brand_id"),
    PRODUCT_ID("product_id"),

    ADDRESS("address"),
    BRAND("brand"),
    CATEGORY("category"),
    CITY("city"),
    CHARACTERISTIC("characteristic"),
    IMAGE("image"),
    NAME("name"),
    PRICE("price"),
    QRCODE("qrcode"),
    STATE("state"),
    STOCK("stock"),

    private final String value

    private Keywords(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }

}