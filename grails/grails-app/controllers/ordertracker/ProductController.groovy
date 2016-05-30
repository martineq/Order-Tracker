package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.queries.RawQueryFacade
import ordertracker.constants.Paths

class ProductController {

    def index() {
        def productsAll = Product.list(sort:"name", order:"des")
        def brands=[]
        
        productsAll.each { product ->
                def br = Brand.findById(product.brand_id)
                brands.add(br)
        }
        
        [res:1,products:productsAll,brands:brands]
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
    
    def up(){
        def cats = Category.list(sort:"name", order:"des")
        def brands = Brand.list(sort:"name", order:"des")
        [cats:cats,brands:brands]
    }
    
    def editproduct(){
    
        def cats = Category.list(sort:"name", order:"des")
        def brands = Brand.list(sort:"name", order:"des")
        [cats:cats,brands:brands]
    }
    
    def editedproduct(){
    
    
      def uplurl= Paths.PRODUCTS.toString()
      def file=request.getFile('image')
      
      def res=0;
                
      def product = Product.get(params.id)
      product.name=params.name
      
      def brands= Brand.list();
      
      product.image=uplurl
      def bra=-1
      
      brands.each { brand ->
                    if( brand.name.toLowerCase().equals(params.brand.toLowerCase()) ){
                        bra=brand.id
                    }
      }
      
      
      product.brand_id=bra
      product.category=params.category
      product.characteristic=params.charac
      product.price=Double.parseDouble(params.price) 
      product.stock=Integer.parseInt(params.stock)
      product.state="disponible"
    
      product.save(failOnError: true)
        
        
      try{
         if(file && !file.empty){
            file.transferTo(new File(uplurl+product.id))
            def b2 = Product.get(product.id)
        
            b2.image="images/product/"+product.id
            
            b2.save(failOnError: true)
            res=1;

         }
         
      }
      catch(Exception e){
         log.error("ERROR subiendo imagen para marca ${params.name}",e)  
         res=0;
         [res:res]
      }
      
      [res:res,name:product.name]
    }
    
      
    def uploadproduct() {
    
    
      def uplurl= Paths.PRODUCTS.toString()
      def file=request.getFile('image')
      
      def res=0;
                
      def product = new Product()
      product.name=params.name
      
      def brands= Brand.list();
      
      product.image=uplurl
      def bra=-1
      
      brands.each { brand ->
                    if( brand.name.toLowerCase().equals(params.brand.toLowerCase()) ){
                        bra=brand.id
                    }
      }
      
      
      product.brand_id=bra
      product.category=params.category
      product.characteristic=params.charac
      product.price=Double.parseDouble(params.price) 
      product.stock=Integer.parseInt(params.stock)
      product.state="disponible"
    
      product.save(failOnError: true)
        
        
      try{
         if(file && !file.empty){
            file.transferTo(new File(uplurl+product.id))
            def b2 = Product.get(product.id)
        
            b2.image="images/product/"+product.id
            
            b2.save(failOnError: true)
            res=1;

         }
         else{
                def pid=product.id
                Product.executeUpdate("delete Product where id=${pid}")
                res=0;
         }
      }
      catch(Exception e){
         log.error("ERROR subiendo imagen para marca ${params.name}",e)  
         res=0;
         [res:res]
      }
      
      [res:res]

    }
    
    def deleteconfirm() {
    
        def discountconflict=false
        
        def prodid=params.id
        
        def result1= Discount.executeQuery("select t1.id from Discount t1 where t1.product_id = ${prodid}")

        if (result1.size() != 0) {
                  discountconflict=true  
        }

        
        [discountconflict:discountconflict]
        
    }
    
    def delete() {
        def pid=params.id
        
        Product.executeUpdate("delete Product where id=${pid}")
        
        [product:params.id]
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
