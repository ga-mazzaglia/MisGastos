package com.cuentasclaras

import grails.util.Environment

class MainController {

    LoginService loginService
    MainService mainService
    MovementService movementService

    def login() {
        grailsApplication.mainContext.getBean("loginService")
        User userLogged = loginService.getUserLogged();
        if (userLogged) {
            redirect url: params.back ?: "/main"
            return;
        }
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
        if (!userLogged) {
            redirect url: "/login?back=" + "/main"
            return;
        }
        def items = movementService.getDebtsByFriends();
        render(view: "index", model: [
                items: items
        ])
    }

    def movementList() {
        User userLogged = loginService.getUserLogged();
        if (!userLogged) {
            redirect url: "/login?back=" + "/movement/list"
            return;
        }
        def movements = movementService.getList();
        render(view: "movementList", model: [
                movements: movements
        ])
    }

    def movementCreate() {
        User userLogged = loginService.getUserLogged();
        if (!userLogged) {
            redirect url: "/login?back=" + "/movement/create"
            return;
        }

        def friends = mainService.getFriends(userLogged.id);
        def tags = mainService.getTags(userLogged.id);
        def movTypes = mainService.getMovementTypes();

        render(view: "movementDetail", model: [
                friends      : friends,
                tags         : tags,
                movementTypes: movTypes,
        ])
    }

}
