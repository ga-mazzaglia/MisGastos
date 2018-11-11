package com.cuentasclaras

import com.cuentasclaras.commands.MovementListCommand
import grails.util.Environment

class MainController {

    LoginService loginService
    MovementService movementService
    TagService tagService

    def login() {
        grailsApplication.mainContext.getBean("loginService")
        User userLogged = loginService.getUserLogged()
        if (userLogged) {
            redirect url: params.back ?: "/"
            return
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
        Map args = params + request.JSON

        User userLogged = loginService.getUserLogged()
        if (!userLogged) {
            redirect url: "/login?back=/" + getQueryParams(args)
            return
        }
        def items = movementService.getDebtsByFriends()
        render(view: "index", model: [
                items: items
        ])
    }

    def movementList() {
        Map args = params + request.JSON

        User userLogged = loginService.getUserLogged()
        if (!userLogged) {
            redirect url: "/login?back=/movement/list" + getQueryParams(args)
            return
        }

        def tags = tagService.getTags()
        MovementListCommand movementListCommand = new MovementListCommand()
        bindData(movementListCommand, args)
        def result = movementService.getList(movementListCommand)
        render(view: "movementList", model: [
                movements: result.movements,
                period   : [
                        dateIni: result.period.ini,
                        dateEnd: result.period.end
                ],
                tags     : tags
        ])
    }

    def movementCreate() {
        Map args = params + request.JSON

        User userLogged = loginService.getUserLogged()
        if (!userLogged) {
            String path = "/movement/create"
            if (params.id) {
                path = "/movement/edit/" + params.id
            }
            redirect url: "/login?back=${path}" + getQueryParams(args)
            return
        }

        def movement = movementService.get(params.id as Long)
        def friends = userLogged.friends
        def tags = tagService.getTags()
        def movTypes = movementService.getMovementTypes()

        render(view: "movementDetail", model: [
                mov          : movement.response,
                friends      : friends,
                tags         : tags,
                movementTypes: movTypes,
        ])
    }

    def tagList() {
        Map args = params + request.JSON

        User userLogged = loginService.getUserLogged()
        if (!userLogged) {
            redirect url: "/login?back=/tag/list" + getQueryParams(args)
            return
        }

        def tags = tagService.getTags()
        render(view: "tagList", model: [
                tags: tags
        ])
    }

    def tagEdit() {
        Map args = params + request.JSON

        User userLogged = loginService.getUserLogged()
        if (!userLogged) {
            redirect url: "/login?back=/tag/edit/" + args.id
            return
        }

        def tag = tagService.getTag(args.id as Long)
        render(view: "tagDetail", model: [
                tag         : tag,
                lastPosition: tagService.getLastPosition()
        ])
    }

    def tagRemove() {
        Map args = params + request.JSON

        User userLogged = loginService.getUserLogged()
        if (!userLogged) {
            redirect url: "/login?back=/tag/list"
            return
        }

        tagService.changeStatus(params.id as Long, false)
        this.tagList()
    }

    def tagSave() {
        Map args = params + request.JSON

        User userLogged = loginService.getUserLogged()
        if (!userLogged) {
            redirect url: "/login?back=/tag/list"
            return
        }

        if (!params.id)
            tagService.create(params.detail as String, params.position as Integer)
        else
            tagService.edit(params.id as Long, params.detail as String, params.position as Integer, params.enabled as Boolean)
        this.tagList()
    }

    def statistics() {
        Map args = params + request.JSON

        User userLogged = loginService.getUserLogged()
        if (!userLogged) {
            redirect url: "/login?back=/statistics" + getQueryParams(args)
            return
        }

        MovementListCommand movementListCommand = new MovementListCommand()
        bindData(movementListCommand, args)
        def result = movementService.getStatistics(movementListCommand)

        render(view: "statistics", model: result)
    }

    // --- AUX ---

    private String getQueryParams(Map args) {
        List attrs = []
        args.each { k, v ->
            if (!(k in ["action", "controller", "tags"]) && v) {
                attrs << "$k=$v"
            }
            if (k == "tags" && v) {
                v.each {
                    attrs << "$k=$it"
                }
            }
        }
        if (attrs.size()) {
            return "?" + attrs.join('&')
        }
        return ""
    }

}
