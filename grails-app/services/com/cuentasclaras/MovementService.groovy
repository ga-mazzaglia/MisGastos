package com.cuentasclaras

import com.cuentasclaras.commands.MovementEditCommand
import com.cuentasclaras.utils.Logger
import org.apache.http.HttpStatus

class MovementService {

    LoginService loginService;

    def List<Movement> getList(){
        User user = loginService.getUserLogged();
        List<Movement> movements = Movement.findAllByUser(user);
        return movements;
    }

    def Map save(MovementEditCommand movementEdit) {
        Movement movement = null;
        try {
            movement = new Movement();
            movement.user = loginService.getUserLogged();
            movement.lastUpdate = new Date();
            movement.date = new Date();
            movement.detail = movementEdit.detail;
            movement.amount = new Double(movementEdit.amount);
            movement.type = MovementType.get(movementEdit.type as Long);
            movement.save(flush: true, failOnError: true);
            return [status: HttpStatus.SC_OK, response: movement]
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error(movementEdit.getValues(), "MovementService.save() Exception: " + ex.getMessage());
            return [status: HttpStatus.SC_INTERNAL_SERVER_ERROR, response: [message: ex.getMessage()]]
        }
        return movement;
    }

}
