package ordertracker

class ProductLoader {
    def load() {
        new Product(name:'mesa', brand_id:1, image:'images/product/1', category:'muebles', characteristic:'madera aglomerada', stock:20, price:600.00, state:'disponible').save()
        new Product(name:'silla', brand_id:1, image:'images/product/2', category:'muebles', characteristic:'madera aglomerada',stock:500, price:150.00, state:'disponible').save()
        new Product(name:'escritorio', brand_id:1, image:'images/product/3', category:'muebles', characteristic:'madera aglomerada', stock:10, price:1500.00, state:'disponible').save()
        new Product(name:'mesa', brand_id:2, image:'images/product/4', category:'muebles', characteristic:'madera roble', stock:5, price:600.00, state:'disponible').save()
        new Product(name:'Coca-Cola', brand_id:3, image:'images/product/5', category:'bebidas', characteristic:'botella 500ml', stock:500, price:15.50, state:'disponible').save()
        new Product(name:'Coca-Cola', brand_id:3, image:'images/product/6', category:'bebidas', characteristic:'botella 1500ml', stock:700, price:23.65, state:'disponible').save()
        new Product(name:'Fanta', brand_id:3, image:'images/product/7', category:'bebidas', characteristic:'botella 1500ml', stock:800, price:24.43, state:'disponible').save()
        new Product(name:'Sprite', brand_id:3, image:'images/product/8', category:'bebidas', characteristic:'botella 1500ml', stock:800, price:23.85, state:'disponible').save()
        new Product(name:'luminaria led', brand_id:4, image:'images/product/9', category:'iluminacion', characteristic:'Aplique exterior gris oscuro', stock:30, price:1850, state:'disponible').save()
        new Product(name:'sarten', brand_id:5, image:'images/product/10', category:'cocina', characteristic:'Sarten Profesional 30cm Acero Inoxidable 18/10', stock:100, price:1350, state:'disponible').save()
        new Product(name:'disco ssd', brand_id:6, image:'images/product/11', category:'electronica', characteristic:'Disco Rígido Estado Solido Ssd 240gb', stock:20, price:2600, state:'disponible').save()
        new Product(name:'atornillador electrico', brand_id:7, image:'images/product/12', category:'construccion', characteristic:'Kit Atornillador 110 Piezas Maletin', stock:30, price:23.85, state:'disponible').save()
        new Product(name:'reloj', brand_id:8, image:'images/product/13', category:'electronica', characteristic:'Fitbit Surge Gps Activity Tracking Watch (large)', stock:5, price:8500, state:'disponible').save()
        new Product(name:'luminaria', brand_id:9, image:'images/product/14', category:'iluminacion', characteristic:'Araña En Hierro Forjado', stock:50, price:3250, state:'disponible').save()
        new Product(name:'sarten', brand_id:5, image:'images/product/15', category:'cocina', characteristic:'Sarten Essen 24 Cm - Linea Clasica - Con Antiadherente', stock:80, price:2500, state:'disponible').save()
        new Product(name:'camara digital', brand_id:10, image:'images/product/16', category:'electronica', characteristic:'Cámara Digital Nikon 1 J3 + Lente 10-100mm 1080p 14mp', stock:8, price:10000, state:'disponible').save()
        new Product(name:'perfume', brand_id:11, image:'images/product/17', category:'ciudado personal', characteristic:'botella 1500ml', stock:800, price:23.85, state:'disponible').save()
    }
}



