package ordertracker

class OrderDetailLoader {
    def load() {
        new OrderDetail(order_id:1, product_id:1, number_of_items:1).save()
        new OrderDetail(order_id:1, product_id:2, number_of_items:10).save()
        new OrderDetail(order_id:1, product_id:3, number_of_items:15).save()
        new OrderDetail(order_id:2, product_id:1, number_of_items:2).save()
        new OrderDetail(order_id:2, product_id:3, number_of_items:18).save()
        new OrderDetail(order_id:3, product_id:1, number_of_items:1).save()
        new OrderDetail(order_id:3, product_id:2, number_of_items:5).save()
        new OrderDetail(order_id:3, product_id:3, number_of_items:30).save()
    }
}
