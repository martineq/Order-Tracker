 package ordertracker
 
class LoginInterceptor {

     public LoginInterceptor() {

        //matchAll().excludes(controller: 'useradmin')
        match(controller:"client", action:"index")
        match(controller:"client", action:"up")
        match(controller:"client", action:"save")
        match(controller:"client", action:"editclient")
        match(controller:"client", action:"listdelete")
        match(controller:"client", action:"updateclient")
        match(controller:"client", action:"deleteconfirm")
        match(controller:"client", action:"delete")
        match(controller:"client", action:"clientdetails")
        match(controller:"client", action:"searchnameclient")
        
        
        
        match(controller:"agenda", action:"index")
        match(controller:"agenda", action:"editagenda")
        match(controller:"agenda", action:"savechanges")
        match(controller:"agenda", action:"deleteconfirm")
        match(controller:"agenda", action:"delete")
        match(controller:"agenda", action:"selectclient")
        match(controller:"agenda", action:"selectnewclient")
        match(controller:"agenda", action:"searchnameclient")
        match(controller:"agenda", action:"up")
        match(controller:"agenda", action:"show")
        match(controller:"agenda", action:"selectdate")
        match(controller:"agenda", action:"upentry")
        match(controller:"agenda", action:"changeseller")
        match(controller:"agenda", action:"selectnewseller")
        
        
        match(controller:"discount", action:"index")
        match(controller:"discount", action:"deleteconfirm")
        match(controller:"discount", action:"delete")
        match(controller:"discount", action:"editdiscount")
        match(controller:"discount", action:"upbrand")
        match(controller:"discount", action:"selectbrand")
        match(controller:"discount", action:"adddiscount")
        match(controller:"discount", action:"upproduct")
        match(controller:"discount", action:"selectproduct")
        match(controller:"discount", action:"newdiscount")
        match(controller:"discount", action:"newdiscountbrand")
        match(controller:"discount", action:"upentrybrandcat")
        match(controller:"discount", action:"upentryproduct")
        
        
        match(controller:"order", action:"index")
        match(controller:"order", action:"orderdetails")
        match(controller:"order", action:"changeorderstate")
        match(controller:"order", action:"statechanged")
        
        
        
        match(controller:"seller", action:"index")
        match(controller:"seller", action:"up")
        match(controller:"seller", action:"save")
        match(controller:"seller", action:"updateseller")
        match(controller:"seller", action:"deleteconfirm")
        match(controller:"seller", action:"editseller")
        match(controller:"seller", action:"delete")
        
        
        
        match(controller:"index", action:"index")
    }
    
    boolean before() {
        // if the user has not been authenticated,
        // redirect to authenticate the user...
        if(!session.user) {
            redirect controller: 'Useradmin', action: 'login'
            return false
        }
        true
    }
    
}