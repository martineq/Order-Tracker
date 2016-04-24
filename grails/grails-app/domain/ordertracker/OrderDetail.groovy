package ordertracker

class OrderDetail {

    static constraints = {
        order_id min:1
        product_id min:1
        number_of_items min:0
        requested_items min:1
    }

    int order_id
    int product_id
    int number_of_items
    int requested_items

}
