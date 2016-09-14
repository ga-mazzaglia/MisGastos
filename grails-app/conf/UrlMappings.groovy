class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/detail/$id?"(controller: "Test", parseRequest: true) {
            action = [POST: "notSupported", GET: "viewProduct", PUT: "notSupported", DELETE: "notSupported"]
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
