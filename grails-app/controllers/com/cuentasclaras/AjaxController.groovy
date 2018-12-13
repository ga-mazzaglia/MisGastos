package com.cuentasclaras

import com.cuentasclaras.commands.LoginCommand
import com.cuentasclaras.commands.MovementDeleteCommand
import com.cuentasclaras.commands.MovementEditCommand
import grails.converters.JSON
import org.apache.http.HttpStatus

class AjaxController {

    LoginService loginService
    MovementService movementService
    TagService tagService

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
        Map result = [:];
        def args = params + request.JSON;

        MovementEditCommand movementEdit = new MovementEditCommand();
        bindData(movementEdit, args);
        movementEdit.validate();
        Map errors = movementEdit.getTheErrors();
        if (errors.size()) {
            result = [status: HttpStatus.SC_BAD_REQUEST, response: [message: "$errors"]]
        } else {
            result = movementService.save(movementEdit);
        }
        response.status = result.status;

        render result as JSON
    }

    def movementDelete() {
        Map result = [:];
        def args = params + request.JSON;

        MovementDeleteCommand movementDelete = new MovementDeleteCommand();
        bindData(movementDelete, args);
        movementDelete.validate();
        Map errors = movementDelete.getTheErrors();
        if (errors.size()) {
            result = [status: HttpStatus.SC_BAD_REQUEST, response: [message: "$errors"]]
        } else {
            result = movementService.delete(movementDelete);
        }
        response.status = result.status;

        render result as JSON
    }

    def movementDetail() {
        Map result = [:];
        def args = params + request.JSON;

        result = movementService.get(args.id as Long);
        response.status = result.status;

        render result as JSON
    }

    def movementAddTag() {
        Map result = [:];
        def args = params + request.JSON;

        result = movementService.addTag(args.mov_id as Long, args.tag_id as Long, (args.added as Long) as Boolean);
        response.status = result.status;

        render result as JSON
    }

    def chartInfo() {
        Map result = [:];

        def byYear = movementService.getStatisticsByYear()
        def tags = tagService.getTags()

        result.byYear = byYear
        result.tags = tags*.detail
        render result as JSON
    }

}
