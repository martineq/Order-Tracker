package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.constants.Paths

class BrandController {

    def index() {
        def brandsAll = Brand.list(sort:"name", order:"des")
        def res=0
        def brands=[]
        
        if(params.name==null) {
                res=1
                [brands:brandsAll,res:res]   
        }
        else {
            brandsAll.each { brand ->
                    if( brand.name.toLowerCase().contains(params.name.toLowerCase()) ){
                        brands.add(brand);
                        res=res+1
                    }
            }
            [brands:brands,res:res]
        }

    }
    
    def viewpic() {
            if(params.img != 'not') {
                def file = new File(params.img)
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
    
    def up() {
    
    }
    
    
    def upload_image() {
    
    
      def uplurl= Paths.BRANDS.toString()
      def file=request.getFile('image')
      
      def res=0;
                
      def brand = new Brand()
      brand.name=params.name
      brand.image=uplurl
      brand.save(failOnError: true)
        
        
      try{
         if(file && !file.empty){
            file.transferTo(new File(uplurl+brand.id))
            def b2 = Brand.get(brand.id)
        
            b2.image=uplurl+brand.id
            b2.save(failOnError: true)
            res=1;

         }
         else{
                def pid=brand.id
                Brand.executeUpdate("delete Brand where id=${pid}")
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
        def productconflict=false
        
        def brandid=params.id
        
        def result1= Discount.executeQuery("select t1.id from Discount t1 where t1.brand_id = ${brandid}")
        def result2= Product.executeQuery("select t2.id from Product t2 where t2.brand_id = ${brandid}")

        if (result1.size() != 0) {
                  discountconflict=true  
        }
        if (result2.size() != 0) {
                  productconflict=true  
        }
        
        [discountconflict:discountconflict,productconflict:productconflict]
        
    }
    

    def delete() {
        def pid=params.id
        
        Brand.executeUpdate("delete Brand where id=${pid}")
        
        [brand:params.id]
    }

    def editbrand(){
    }
    
    def saveeditedbrand(){
    
        def brand = Brand.get(params.id)
        brand.name=params.name
        brand.save(failOnError: true)
         
         
        def uplurl= Paths.BRANDS.toString()
        def file=request.getFile('image')
        
        try{
            if(file && !file.empty){
                file.transferTo(new File(uplurl+brand.id))
                def b2 = Brand.get(brand.id)
            
                b2.image=uplurl+brand.id
                b2.save(failOnError: true)

            }
        }
        catch(Exception e){
            log.error("ERROR subiendo imagen para marca ${params.name}",e)  
        }
        
        [name:params.name]
        
    }
    
    
    
}
 
