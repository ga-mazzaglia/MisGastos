package com.cuentasclaras

import com.cuentasclaras.commands.MovementDeleteCommand
import com.cuentasclaras.commands.MovementEditCommand
import com.cuentasclaras.utils.Logger
import org.apache.http.HttpStatus

class MovementService {

    LoginService loginService;

    def List<Movement> getList(){
        User user = loginService.getUserLogged();
        List<Movement> movements = Movement.findAllByUserAndDeleted(user, false, [sort: "date", order: "desc"]);
        return movements;
    }

    def Map save(MovementEditCommand movementEdit) {
        try {
            Movement movement = new Movement();
            movement.user = loginService.getUserLogged();
            movement.lastUpdate = new Date();
            movement.date = new Date();
            movement.detail = movementEdit.detail;
            movement.amount = movementEdit.amount;
            movement.type = MovementType.get(movementEdit.type as Long);
            movement.save(flush: true, failOnError: true);
            movementEdit.friends.each { Long userId ->
                movement.addToUsers(User.get(userId));
            }
            return [status: HttpStatus.SC_OK, response: movement]
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error(movementEdit.getValues(), "MovementService.save() Exception: " + ex.getMessage());
            return [status: HttpStatus.SC_INTERNAL_SERVER_ERROR, response: [message: ex.getMessage()]]
        }
    }

    def Map delete(MovementDeleteCommand movementDelete) {
        try {
            Movement movement = Movement.get(movementDelete.id);
            movement.deleted = true;
            movement.save(flush: true, failOnError: true);
            return [status: HttpStatus.SC_OK, response: movement]
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error(movementDelete.getValues(), "MovementService.delete() Exception: " + ex.getMessage());
            return [status: HttpStatus.SC_INTERNAL_SERVER_ERROR, response: [message: ex.getMessage()]]
        }
    }

    def Map get(Long movId) {
        try {
            Movement movement = Movement.get(movId);
            Map movementInfo = movement.getValues();
            return [status: HttpStatus.SC_OK, response: movementInfo]
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error([id: movId], "MovementService.delete() Exception: " + ex.getMessage());
            return [status: HttpStatus.SC_INTERNAL_SERVER_ERROR, response: [message: ex.getMessage()]]
        }
    }

}
