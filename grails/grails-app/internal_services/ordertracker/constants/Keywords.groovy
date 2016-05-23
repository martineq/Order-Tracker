package ordertracker.constants

enum Keywords {

    USERNAME("username"),
    PASSWORD("password"),
    TOKEN("token"),

    CLIENT_ID("client_id"),
    USERNAME_ID("username_id"),
    BRAND_ID("brand_id"),
    PRODUCT_ID("product_id"),

    VISITED_CLIENTS_ES("clientesVisistados"),
    OUT_OF_ROUTE_VISITED_CLIENTS_ES("clientesFueraDeRuta"),
    TOTAL_SOLD_UNITS_ES("bultosVendidos"),
    TODAY_GROSS_SALES_ES('ventasDelDia'),

    ADDRESS("address"),
    BODY("body"),
    BRAND("brand"),
    CATEGORY("category"),
    CLIENT('client'),
    CITY("city"),
    CHARACTERISTIC("characteristic"),
    DATA('data'),
    DATE("date"),
    DATE_FROM("date_from"),
    DATE_UPTO("date_upto"),
    DESCRIPTION("description"),
    DISCOUNT('discounts'),
    ERROR('error'),
    FAILURE('failure'),
    GCM_TOKEN('tokengcm'),
    ID("id"),
    IMAGE("image"),
    IMAGE_BASE_64("imageBase64"),
    LINES('lines'),
    NAME("name"),
    NOTIFICATION("notification"),
    ORDER('order'),
    PERCENTAGE('percentage'),
    PRICE("price"),
    PRODUCT('product'),
    QUANTITY('quantity'),
    QR('qr'),
    QRCODE("qrcode"),
    RANGE_FROM('range_from'),
    RANGE_UPTO('range_upto'),
    RESULTS('results'),
    STATE("state"),
    STOCK("stock"),
    SUCCESS('success'),
    TITLE("title"),
    TO('to'),
    TYPE('type'),
    VISIT_ID('visit_id'),
    WEEK_DATE('week_date'),

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