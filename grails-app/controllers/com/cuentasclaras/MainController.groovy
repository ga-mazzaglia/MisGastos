package com.cuentasclaras

import grails.util.Environment

class MainController {

    LoginService loginService
    MainService mainService
    MovementService movementService

    def login() {
        render(view: "login", model: [
                action  : "login",
                scope   : Environment.current.name.toLowerCase(),
                url_base: grailsApplication.config.urls.base
        ])
    }

    def logout() {
        loginService.logout()
        render(view: "login", model: [
                scope: Environment.current.name.toLowerCase()
        ])
    }

    def home() {
        User userLogged = loginService.getUserLogged();
        if (userLogged) {
            redirect url: "/login?back=" + "/main"
            return;
        }
        render(view: "index")
    }

    def movementList() {
        User userLogged = loginService.getUserLogged();
        if (!userLogged) {
            redirect url: "/login?back=" + "/movement/list"
            return;
        }
        def movements = movementService.getList();
        render(view: "movementList", model :[
                movements: movements
        ])
    }

    def movementCreate() {
        User userLogged = loginService.getUserLogged();
        if (!userLogged) {
            redirect url: "/login?back=" + "/movement/create"
            return;
        }

        def friends = mainService.getFriends(1);
        def tags = mainService.getTags(1);
        def movTypes = mainService.getMovementTypes();

        render(view: "movementDetail", model: [
                friends      : friends,
                tags         : tags,
                movementTypes: movTypes,
        ])
    }

}
