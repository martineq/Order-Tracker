package ordertracker

class OrderDetailLoader {
    def load() {
        def product1 = Product.findById(1)
        def brand1 = Brand.findById(product1.brand_id)
        new OrderDetail(order_id:1, product_id:1, requested_items:4,productname:product1.name,brand:brand1.name,characteristic:product1.characteristic,category:product1.category,price:product1.price).save()
        
        def product2 = Product.findById(2)
        def brand2 = Brand.findById(product2.brand_id)
        new OrderDetail(order_id:1, product_id:2, requested_items:1,productname:product2.name,brand:brand2.name,characteristic:product2.characteristic,category:product2.category,price:product2.price).save()
        
        
        def product3 = Product.findById(3)
        def brand3 = Brand.findById(product3.brand_id)
        new OrderDetail(order_id:1, product_id:3, requested_items:4,productname:product3.name,brand:brand3.name,characteristic:product3.characteristic,category:product3.category,price:product3.price).save()
        
        
        
        
        def product4 = Product.findById(4)
        def brand4 = Brand.findById(product4.brand_id)
        new OrderDetail(order_id:2, product_id:4, requested_items:4,productname:product4.name,brand:brand4.name,characteristic:product4.characteristic,category:product4.category,price:product4.price).save()
        
        def product5 = Product.findById(5)
        def brand5 = Brand.findById(product5.brand_id)
        new OrderDetail(order_id:2, product_id:5, requested_items:1,productname:product5.name,brand:brand5.name,characteristic:product5.characteristic,category:product5.category,price:product5.price).save()
        
        
        new OrderDetail(order_id:2, product_id:3, requested_items:4,productname:product3.name,brand:brand3.name,characteristic:product3.characteristic,category:product3.category,price:product3.price).save()
        
        
        
        
        def product6 = Product.findById(6)
        def brand6 = Brand.findById(product6.brand_id)
        new OrderDetail(order_id:3, product_id:6, requested_items:6,productname:product6.name,brand:brand6.name,characteristic:product6.characteristic,category:product6.category,price:product6.price).save()
        

        new OrderDetail(order_id:3, product_id:5, requested_items:1,productname:product5.name,brand:brand5.name,characteristic:product5.characteristic,category:product5.category,price:product5.price).save()
        
        
        new OrderDetail(order_id:3, product_id:3, requested_items:2,productname:product3.name,brand:brand3.name,characteristic:product3.characteristic,category:product3.category,price:product3.price).save()
        
     
     
     
     
     
        new OrderDetail(order_id:4, product_id:6, requested_items:2,productname:product6.name,brand:brand6.name,characteristic:product6.characteristic,category:product6.category,price:product6.price).save()
        
        def product7 = Product.findById(7)
        def brand7 = Brand.findById(product4.brand_id)
        
        new OrderDetail(order_id:4, product_id:7, requested_items:10,productname:product7.name,brand:brand7.name,characteristic:product7.characteristic,category:product7.category,price:product7.price).save()
        
        new OrderDetail(order_id:4, product_id:1, requested_items:2,productname:product1.name,brand:brand1.name,characteristic:product1.characteristic,category:product1.category,price:product1.price).save()
        
        
        
        
        
        def product8 = Product.findById(8)
        def brand8 = Brand.findById(product8.brand_id)
        new OrderDetail(order_id:5, product_id:8, requested_items:4,productname:product8.name,brand:brand8.name,characteristic:product8.characteristic,category:product8.category,price:product8.price).save()
        
        def product9 = Product.findById(9)
        def brand9 = Brand.findById(product9.brand_id)
        new OrderDetail(order_id:5, product_id:9, requested_items:3,productname:product9.name,brand:brand9.name,characteristic:product9.characteristic,category:product9.category,price:product9.price).save()
        
        
        def product10 = Product.findById(10)
        def brand10 = Brand.findById(product10.brand_id)
        new OrderDetail(order_id:5, product_id:10, requested_items:4,productname:product10.name,brand:brand10.name,characteristic:product10.characteristic,category:product10.category,price:product10.price).save()
        
    }
}
