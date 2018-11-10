class UrlMappings {

    static mappings = {

        //-- VIEWS --

        "/"(controller: "Main", parseRequest: true) {
            action = [GET: "home"]
        }

        "/login"(controller: "Main", parseRequest: true) {
            action = [GET: "login"]
        }
        "/logout"(controller: "Main", parseRequest: true) {
            action = [GET: "logout"]
        }

        //-- MOVEMENTS --

        "/movement/list"(controller: "Main", parseRequest: true) {
            action = [GET: "movementList"]
        }
        "/movement/create"(controller: "Main", parseRequest: true) {
            action = [GET: "movementCreate"]
        }
        "/movement/edit/$id"(controller: "Main", parseRequest: true) {
            action = [GET: "movementCreate"]
        }

        //-- TAGS --

        "/tag/list"(controller: "Main", parseRequest: true) {
            action = [GET: "tagList"]
        }
        "/tag/edit/$id"(controller: "Main", parseRequest: true) {
            action = [GET: "tagEdit"]
        }
        "/tag/create"(controller: "Main", parseRequest: true) {
            action = [GET: "tagEdit"]
        }
        "/tag/remove/$id"(controller: "Main", parseRequest: true) {
            action = [GET: "tagRemove"]
        }
        "/tag/save"(controller: "Main", parseRequest: true) {
            action = [POST: "tagSave"]
        }

        //-- AJAX --

        "/ajax/signin"(controller: "Ajax", parseRequest: true) {
            action = [POST: "signIn"]
        }
        "/ajax/movement/save"(controller: "Ajax", parseRequest: true) {
            action = [POST: "movementSave"]
        }
        "/ajax/movement/delete"(controller: "Ajax", parseRequest: true) {
            action = [POST: "movementDelete"]
        }
        "/ajax/movement/$id?"(controller: "Ajax", parseRequest: true) {
            action = [GET: "movementDetail"]
        }
        "/ajax/movement/tag"(controller: "Ajax", parseRequest: true) {
            action = [POST: "movementAddTag"]
        }

        //-- PING --

        "/ping"(controller: "Health", parseRequest: true) {
            action = [GET: "ping"]
        }

        //-- GENERIC --

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "500"(view: '/error')
    }
}
