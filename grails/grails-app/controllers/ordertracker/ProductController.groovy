package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.queries.RawQueryFacade
import ordertracker.constants.Paths

class ProductController {

    def index() {
        def productsAll = Product.list(sort:"name", order:"des")
        def brands=[];
        def products=[];
        
        String namesearch=""
        String brandsearch=""
        String catsearch=""
        
        int res=0
        
        int stockinit=0
        int stockend=999999999
        
        int priceinit=0
        int priceend=999999999

        def brandslist= Brand.executeQuery("select t2.name from Brand t2 order by t2.name");
        def cats= Category.executeQuery("select t1.name from Category t1 order by t1.name");
        
        brandslist.add(0,"");
        cats.add(0,"");
        
        if (params.name != null ) {
            if(params.name.length() != 0 ){
                namesearch=params.name.toLowerCase()
            }
        }
        if (params.brand != null ) {
            if(params.brand.length() != 0 ){
                brandsearch=params.brand.toLowerCase()
            }
        }
        if (params.category != null ) {
            if(params.category.length() != 0 ){
                catsearch=params.category.toLowerCase()
            }
        }
        
        if (params.stockinit != null && params.stockend != null) {
            if(params.stockinit.length() != 0  && params.stockend.length() != 0 ){
                    stockinit=params.stockinit.toInteger()
                    stockend=params.stockend.toInteger()
            }
        }
        
        if (params.priceinit != null && params.priceend != null) {
            if(params.priceinit.length() != 0  && params.priceend.length() != 0 ){
                    priceinit=params.priceinit.toInteger()
                    priceend=params.priceend.toInteger()
            }
        }
        
        

        productsAll.each { product ->
        
                def br = Brand.findById(product.brand_id)
                
                boolean containsName=product.name.toLowerCase().contains(namesearch)
                boolean containsCat=product.category.toLowerCase().contains(catsearch)
                boolean containsBrand=br.name.toLowerCase().contains(brandsearch)
                boolean containsStock=containsNumber(product.stock,stockinit,stockend)
                boolean containsPrice=containsNumberD(product.price,priceinit,priceend)

                
                if(containsName&&containsCat&&containsBrand&&containsStock&&containsPrice){
                        brands.add(br)
                        products.add(product)
                        res=res+1
                }
        }
        
        [res:res,products:products,brands:brands,catslist:cats,brandslist:brandslist]
    }
    
    def containsNumber(int number, int init, int end){
        return ( number>=init && number<=end );
    }
    
    def containsNumberD(double number, int init, int end){
        return ( number>=init && number<=end );
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
