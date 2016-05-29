package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.queries.RawQueryFacade
import ordertracker.constants.Paths

class ProductController {

    def index() {
        def productsAll = Product.list(sort:"name", order:"des")
        [res:1,products:productsAll]
    }
    
    def viewpic() {
            def path= Paths.PRODUCTS.toString()
            
            if(params.img != 'not') {
                def file = new File(path+params.id)
                def fileInputStream = new FileInputStream(file)
                def outputStream = response.getOutputStream()
                byte[] buffer = new byte[4096];
                int len;
                while ((len = fileInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush()
                outputStream.close()
                fileInputStream.close()
            }
    }

    def list() {
        response << new QueryFacade(new AvailableProductsService()).solve(request)
    }

    def description() {
        response << new QueryFacade(new ProductDescriptionService()).solve(request)
    }

    def image() {
        response << new RawQueryFacade(new ProductImageService()).solve(request)
    }

    def testAddProduct() {
        new Product(name:'prueba', brand_id:11, image:'images/product/17', category:'ciudado personal', characteristic:'botella 1500ml', stock:800, price:13.85, state:'disponible').save()
    }
}
