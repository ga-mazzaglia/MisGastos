package com.cuentasclaras

import com.cuentasclaras.commands.MovementDeleteCommand
import com.cuentasclaras.commands.MovementEditCommand
import com.cuentasclaras.statics.MovementsType
import com.cuentasclaras.utils.Logger
import org.apache.http.HttpStatus

class MovementService {

    LoginService loginService;

    def Map getDebtsByFriends() {
        try {
            List results = []
            Map amounts = [:];
            User userLogged = loginService.getUserLogged();

            // Movimientos propios compartidos con amigos
            userLogged.friends.each { User friend ->
                List<Movement> movementsFriends = Movement.findAll {
                    deleted == false
                    user.id == userLogged.id
                    users.id == friend.id
                }.findAll { it.type.id == MovementsType.GASTOS_COMPARTIDOS.index }
                movementsFriends.each { Movement mov ->
                    Double amountProp = (mov.amount / (mov.users.size() + 1));

                    if (amounts[(friend.id)] == null) {
                        amounts += [(friend.id): (amountProp * -1)]
                    } else {
                        amounts[(friend.id)] += (amountProp * -1)
                    }
                }
            }

            // Movimientos de amigos compartidos con migo
            userLogged.friends.each { User friend ->
                List<Movement> movementsFriends = Movement.findAll {
                    deleted == false
                    user.id == friend.id
                    users.id == userLogged.id
                }.findAll { it.type.id == MovementsType.GASTOS_COMPARTIDOS.index }
                movementsFriends.each { Movement mov ->
                    Double amountProp = (mov.amount / (mov.users.size() + 1));

                    if (amounts[(friend.id)] == null) {
                        amounts += [(friend.id): amountProp]
                    } else {
                        amounts[(friend.id)] += amountProp
                    }
                }
            }

            // Dinero entregado a un amigo
            userLogged.friends.each { User friend ->
                // dinero entregado a un amigo -
                List<Movement> movementsFriends = Movement.findAll {
                    deleted == false
                    user.id == userLogged.id
                    users.id == friend.id
                    type.id == MovementsType.DINERO_ENTREGADO.index
                }
                movementsFriends.each { Movement mov ->
                    if (amounts[(friend.id)] == null) {
                        amounts += [(friend.id): (mov.amount * -1)]
                    } else {
                        amounts[(friend.id)] += (mov.amount * -1)
                    }
                }
                // dinero entregado de un amigo a mi +
                movementsFriends = Movement.findAll {
                    deleted == false
                    user.id == friend.id
                    users.id == userLogged.id
                    type.id == MovementsType.DINERO_ENTREGADO.index
                }
                movementsFriends.each { Movement mov ->
                    if (amounts[(friend.id)] == null) {
                        amounts += [(friend.id): mov.amount]
                    } else {
                        amounts[(friend.id)] += mov.amount
                    }
                }
            }

            // Dinero recibido de amigo
            userLogged.friends.each { User friend ->
                // dinero recibido de un amigo +
                List<Movement> movementsFriends = Movement.findAll {
                    deleted == false
                    user.id == friend.id
                    users.id == userLogged.id
                    type.id == MovementsType.DINERO_RECIBIDO.index
                }
                movementsFriends.each { Movement mov ->
                    if (amounts[(friend.id)] == null) {
                        amounts += [(friend.id): (mov.amount * -1)]
                    } else {
                        amounts[(friend.id)] += (mov.amount * -1)
                    }
                }
                // dinero enviado a un amigo -
                movementsFriends = Movement.findAll {
                    deleted == false
                    user.id == userLogged.id
                    users.id == friend.id
                    type.id == MovementsType.DINERO_RECIBIDO.index
                }
                movementsFriends.each { Movement mov ->
                    if (amounts[(friend.id)] == null) {
                        amounts += [(friend.id): mov.amount]
                    } else {
                        amounts[(friend.id)] += mov.amount
                    }
                }
            }

            amounts.each { Long userId, Double amount ->
                results << [user: User.get(userId), amount: amount]
            }

            userLogged.friends.each { User friend ->
                if (amounts[(friend.id)] == null) {
                    results << [user: friend, amount: 0.00]
                }
            }

            return [status: HttpStatus.SC_OK, response: results];
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error([:], "MovementService.getDebtsByFriends() Exception: " + ex.getMessage());
            return [status: HttpStatus.SC_INTERNAL_SERVER_ERROR, response: [message: ex.getMessage()]]
        }
    }

    def List<Map> getList() {
        List<Map> results = [];

        User userLogged = loginService.getUserLogged();
        List<Movement> movements = Movement.findAllByUserAndDeleted(userLogged, false, [sort: "date", order: "desc"]);
        movements += Movement.findAll([sort: "date", order: "desc"]) {
            deleted == false
            users.id == userLogged.id
        }

        movements.each { Movement mov ->
            String color = "";
            if (mov.type.id in (Long[]) [1]) { // gasto personal
                color = "blue";
            }
            if (mov.type.id in (Long[]) [2, 3]) { // gastos compartidos | dinero entregado
                color = mov.user.id == userLogged.id ? "red" : "green";
            }
            if (mov.type.id in (Long[]) [4]) { // dinero recibido
                color = mov.user.id == userLogged.id ? "green" : "red";
            }

            String userToDisplay = "";
            if (mov.user.id == userLogged.id) {
                userToDisplay = mov.users*.name.join(", ")
            } else {
                userToDisplay = mov.user.name
            }

            results << [
                    id           : mov.id,
                    user         : mov.user.getValues(),
                    creationDate : mov.creationDate,
                    lastUpdate   : mov.lastUpdate,
                    date         : mov.date,
                    detail       : mov.detail,
                    amount       : mov.amount,
                    deleted      : mov.deleted,
                    type         : mov.type.getValues(),
                    users        : mov.users*.getValues(),
                    color        : color,
                    userToDisplay: userToDisplay
            ];
        }

        return results;
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
