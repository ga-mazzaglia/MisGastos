package com.cuentasclaras

class MainController {

    def mainService

    def index() {
        render(view: "index")
    }

    def movementList() {
        render(view: "movementList")
    }

    def movementCreate() {
        def friends = mainService.getFriends(1);
        def tags = mainService.getTags(1);
        def movTypes = mainService.getMovementTypes();

        render(view: "movementDetail", model: [
                friends      : friends,
                tags         : tags,
                movementTypes: movTypes,
        ])
    }

    def movementSave() {
        println request.JSON
        movementList()
    }

}
