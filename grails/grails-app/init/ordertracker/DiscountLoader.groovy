package ordertracker

class DiscountLoader {
    def load() {
        new Discount(product_id: 1, percentage: 5.00).save
        new Discount(product_id: 2, percentage: 7.50).save
        new Discount(product_id: 3, percentage: 3.33).save
        new Discount(product_id: 4, percentage: 0.00).save
    }
}
