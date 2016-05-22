package ordertracker

import ordertracker.queries.QueryFacade

class SellerController {

    def index() {
    
        def sellersAll = Seller.list(sort:"document_number", order:"des")
        if(params.name==null && params.dni==null) {
            def res=1
            [sellers:sellersAll,res:res]
        }
        else if (params.name.length() == 0 && params.dni.length()==0){
            def res=-1
            [sellers:sellersAll,res:res]
        }
        else {
                def sellers=[]
                def res=0
            
                sellersAll.each { sell ->
                        if (params.dni.length() != 0 && params.name.length() == 0){
                        
                            if( sell.document_number==params.dni.toInteger() ){
                                sellers.add(sell);
                                res=res+1
                            }
                            
                        }
                        if (params.name.length() != 0 && params.dni.length()==0 ){
                            if( sell.name.toLowerCase().contains(params.name.toLowerCase()) ){
                                sellers.add(sell);
                                res=res+1
                            }
                        }
                        if (params.name.length() != 0 && params.dni.length()!=0 ){
                            if( sell.name.toLowerCase().contains(params.name.toLowerCase()) ){
                                if( sell.document_number==params.dni.toInteger() ) {
                                    sellers.add(sell);
                                    res=res+1
                                }
                            }
                        }
                };
            
                [sellers:sellers,res:res]
        
            }
    }
    

    def weeklySchedule() {
        response << new QueryFacade(new WeeklyScheduleService()).solve(request)
    }
    
            
    def up() {
    }
    
    def save() {
        def seller = new Seller()
        seller.name=params.name
        seller.document_number=params.dni.toInteger()
        seller.phone=params.phone.toInteger()
        seller.zone=params.zone
        
        seller.save(failOnError: true)
        def user = new User()
        user.username=params.user
        user.password=params.password
        user.token="token"+params.dni
        
        user.save(failOnError: true)
         
        
        def usertype= new UserType( user_id: user.id,type_id: seller.id, type: Seller.getTypeName())
        usertype.save(failOnError: true)
        
        [sellern:seller.name]
    }
    
    def updateseller() {

        def seller = Seller.get(params.id)
        
        seller.name=params.name
        seller.phone=params.phone.toInteger()
        seller.document_number=params.dni.toInteger()
        seller.zone=params.zone
        
        def types = UserType.list()
        types.each { type ->
                if(type.type_id==seller.id){
                    def user = User.get(type.user_id)
                    user.username=params.user
                    user.password=params.pass
                    user.save(failOnError: true)
                }
        };
        
        seller.save(failOnError: true)

        [seller:seller]
        

    }
    
    def deleteconfirm() {
    }
    
    def editseller() {

        def seller = Seller.findById(params.id)
        
        String user="";
        String pass="";
        
        
        def types = UserType.list()
        types.each { type ->
                if(type.type_id==seller.id){
                    def userC = User.get(type.user_id)
                    user=userC.username
                    pass=userC.password
                }
        };
        
        
        [seller:seller,user:user,pass:pass]

    }
    
    def delete() {
        def pid=params.id
        Seller.executeUpdate("delete Seller where id=${pid}")
        
        //borrar todas las entradas de la agenda para este vendedor
        Agenda.executeUpdate("delete Agenda where seller_id=${pid}")
        
        [clients:params.id]
    }

}
