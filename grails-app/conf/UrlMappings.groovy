class UrlMappings {

    static mappings = {

        "/"(controller: "Test", parseRequest: true) {
            action = [GET: "ping"]
        }

        "/main"(controller: "Main", parseRequest: true) {
            action = [GET: "index"]
        }

        //-- MOVEMENTS --

        "/movement/list"(controller: "Main", parseRequest: true) {
            action = [GET: "movementList"]
        }
        "/movement/create"(controller: "Main", parseRequest: true) {
            action = [GET: "movementCreate"]
        }

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')
    }
}
