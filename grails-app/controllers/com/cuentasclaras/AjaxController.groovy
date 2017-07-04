package com.cuentasclaras

import com.cuentasclaras.commands.LoginCommand
import com.cuentasclaras.commands.MovementEditCommand
import grails.converters.JSON
import org.apache.http.HttpStatus

class AjaxController {

    LoginService loginService
    MovementService movementService

    def signIn() {
        Map args = params + request.JSON;
        LoginCommand signInCommand = new LoginCommand();
        bindData(signInCommand, args);
        signInCommand.validate();
        def result = [:];
        def errors = signInCommand.getTheErrors();
        if (!errors.size()) {
            result = loginService.check(signInCommand);
        } else {
            result = [status: HttpStatus.SC_BAD_REQUEST, response: [message: "$errors"]]
        }
        response.status = result.status
        render result as grails.converters.JSON
    }

    def movementSave() {
        def args = params + request.JSON;

        MovementEditCommand movementEdit = new MovementEditCommand();
        bindData(movementEdit, args);
        println "---"
        println movementEdit
        println "---"
        Map result = movementService.save(movementEdit);
        response.status = result.status;

        render result as JSON
    }

}
