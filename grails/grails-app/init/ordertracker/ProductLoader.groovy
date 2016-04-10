package ordertracker

class ProductLoader {
    def load() {

        new Product(name:'mesa', brand_id:'Velasco', image:'not', category:'muebles', characteristic:'madera aglomerada',
                stock:20, price:600.00, state:'disponible').save()
        new Product(name:'silla', brand_id:'Velasco', image:'not', category:'muebles', characteristic:'madera aglomerada',
                stock:500, price:150.00, state:'disponible').save()
        new Product(name:'escritorio', brand_id:'Velasco', image:'not', category:'muebles', characteristic:'madera aglomerada',
                stock:10, price:1500.00, state:'disponible').save()
        new Product(name:'mesa', brand_id:'HQ', image:'not', category:'muebles', characteristic:'madera roble',
                stock:5, price:600.00, state:'disponible').save()
        new Product(name:'Coca-Cola', brand_id:'Coca-Cola', image:'not', category:'bebidas', characteristic:'botella 500ml',
                stock:500, price:15.50, state:'disponible').save()
        new Product(name:'Coca-Cola', brand_id:'Coca-Cola', image:'not', category:'bebidas', characteristic:'botella 1500ml',
                stock:700, price:23.65, state:'disponible').save()
        new Product(name:'Fanta', brand_id:'Coca-Cola', image:'not', category:'bebidas', characteristic:'botella 1500ml',
                stock:800, price:24.43, state:'disponible').save()

    }
}



