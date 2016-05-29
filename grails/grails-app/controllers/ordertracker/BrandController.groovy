package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.constants.Paths

class BrandController {


    def index() {
        def brands = Brand.list(sort:"name", order:"des")

        [brands:brands]
    }
    
    def up() {
    
    }
    
    
    def upload_image() {
    
      def uplurl= Paths.BRANDS.toString()
      def file=request.getFile('image')
      def res=0
      
      try{
         if(file && !file.empty){
            file.transferTo(new File(uplurl+"100"))
            flash.message="your.sucessful.file.upload.message"
            res=1
            [res:res]
         }
         else{
         flash.message="your.unsucessful.file.upload.message"
         [res:res]
         }
      }
      catch(Exception e){
         log.error("Your exception message goes here",e)  
         [res:res]
      }
      [res:res]

    }

    
}
 
