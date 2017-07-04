class UrlMappings {

    static mappings = {

        //-- VIEWS --

        "/"(controller: "Main", parseRequest: true) {
            action = [GET: "home"]
        }
        "/main"(controller: "Main", parseRequest: true) {
            action = [GET: "home"]
        }

        "/login"(controller: "Main", parseRequest: true) {
            action = [GET: "login"]
        }
        "/logout"(controller: "Main", parseRequest: true) {
            action = [GET: "logout"]
        }

        "/movement/list"(controller: "Main", parseRequest: true) {
            action = [GET: "movementList"]
        }
        "/movement/create"(controller: "Main", parseRequest: true) {
            action = [GET: "movementCreate"]
        }

        //-- AJAX --

        "/ajax/signin"(controller: "Ajax", parseRequest: true) {
            action = [POST: "signIn"]
        }
        "/ajax/movement/save"(controller: "Ajax", parseRequest: true) {
            action = [POST: "movementSave"]
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
