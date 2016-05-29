package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.constants.Paths

class BrandController {

    def index() {
        def brands = Brand.list(sort:"name", order:"des")

        [brands:brands]
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
    }
    
    def delete() {
        def pid=params.id
        Brand.executeUpdate("delete Brand where id=${pid}")
        //TODO deberia ver que no hay productos de esta marca primero
        [brand:params.id]
    }

    
}
 
