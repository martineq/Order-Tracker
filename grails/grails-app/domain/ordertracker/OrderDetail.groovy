package ordertracker

class OrderDetail {

    static constraints = {
        order_id min:1
        product_id min:1
        requested_items min:1
    }

    int order_id
    int product_id
    int requested_items
    String productname
    String brand
    String characteristic
    String category
    double price

}
