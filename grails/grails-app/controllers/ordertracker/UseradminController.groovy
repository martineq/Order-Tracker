package ordertracker


class UseradminController {
    def login = { 
        
    }
    
    def doLogin = {
        def user = Useradmin.findWhere(email:params['email'],password:params['password'])
        session.user = user
        if(user) redirect( uri:'/' )
        else
        redirect(controller:'useradmin',action:'login')
    }
    
    def logout = {
        session.user = null
        redirect(controller:'useradmin',action:'login')
    }

} 
