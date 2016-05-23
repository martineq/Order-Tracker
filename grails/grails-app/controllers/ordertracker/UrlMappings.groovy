package ordertracker

class UrlMappings {

    static mappings = {

        "/$product/$image/$id?"(controller:"product", action:"image")

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:"index", action:"index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
