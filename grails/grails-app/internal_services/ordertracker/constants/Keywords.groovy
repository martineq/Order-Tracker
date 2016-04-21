package ordertracker.constants

enum Keywords {

    USERNAME("username"),
    PASSWORD("password"),
    TOKEN("token"),

    CLIENT_ID("client_id"),
    USERNAME_ID("username_id"),
    BRAND_ID("brand_id"),
    PRODUCT_ID("product_id"),

    ADDRESS("address"),
    BRAND("brand"),
    CATEGORY("category"),
    CLIENT('client'),
    CITY("city"),
    CHARACTERISTIC("characteristic"),
    DATE("date"),
    DESCRIPTION("description"),
    ID("id"),
    IMAGE("image"),
    IMAGE_BASE_64("imageBase64"),
    LINES('lines'),
    NAME("name"),
    ORDER('order'),
    PRICE("price"),
    PRODUCT('product'),
    QUANTITY('quantity'),
    QRCODE("qrcode"),
    STATE("state"),
    STOCK("stock"),

    AR_TIMEZONE("GMT-3:00"),

    DATE_ES('fecha'),
    STATE_ES('estado'),
    TOTAL_AMOUNT_ES('importeTotal'),
    SELLER_ES('vendedor'),

    private final String value

    private Keywords(String value) {
        this.value = value
    }

    @Override
    String toString() {
        return this.value
    }

}