package ordertracker

class DiscountLoader {
    def load() {
        new Discount(product_id: 1, brand_id:1, category:'muebles', percentage: 5.00).save()
        new Discount(product_id: 2, brand_id:1,category:'muebles', percentage: 7.50).save()
        new Discount(product_id: 3, brand_id:2, category:'muebles', percentage: 3.33).save()
    }
}
