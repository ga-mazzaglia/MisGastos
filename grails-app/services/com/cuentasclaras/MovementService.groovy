package com.cuentasclaras

import com.cuentasclaras.commands.MovementDeleteCommand
import com.cuentasclaras.commands.MovementEditCommand
import com.cuentasclaras.commands.MovementListCommand
import com.cuentasclaras.statics.MovementsType
import com.cuentasclaras.utils.Logger
import org.apache.http.HttpStatus

class MovementService {

    LoginService loginService;

    def MovementType[] getMovementTypes() {
        return MovementType.findAll();
    }

    def Tag[] getTags() {
        User userLogged = loginService.getUserLogged();
        return Tag.findAllByUser(userLogged);
    }

    /**
     * Utilizado en la home
     *
     * @return
     */
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

    def List<Map> getList(MovementListCommand movementListCommand) {
        List<Map> results = [];

        Map period = movementListCommand.getFilterPeriod();
        Date dateIni = period.ini;
        Date dateEnd = period.end;

        User userLogged = loginService.getUserLogged();
        List<Movement> movements;
        movements = Movement.findAll([sort: "date", order: "desc"]) {
            deleted == false
            user == userLogged
            if (dateIni) {
                date >= dateIni
            }
            if (dateEnd) {
                date <= dateEnd
            }
            if (movementListCommand.search) {
                like("detail", "%${movementListCommand.search}%")
            }
        }
        movements += Movement.findAll([sort: "date", order: "desc"]) {
            deleted == false
            users.id == userLogged.id
            if (dateIni) {
                date >= dateIni
            }
            if (dateEnd) {
                date <= dateEnd
            }
            if (movementListCommand.search) {
                like("detail", "%${movementListCommand.search}%")
            }
        }

        movements = movements.sort { it.date }.reverse();

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
            Movement movement = Movement.get(movementEdit.id);
            if (!movement) {
                movement = new Movement();
                movement.user = loginService.getUserLogged();
                Date creationDate = new Date();
                use(groovy.time.TimeCategory){
                    creationDate -= 3.hours;
                }
                movement.creationDate = creationDate;
            }
            Date lastUpdate = new Date();
            use(groovy.time.TimeCategory){
                lastUpdate -= 3.hours;
            }
            movement.lastUpdate = lastUpdate;
            Date date = null;
            try {
                date = new Date().parse("dd/MM/yyyy", movementEdit.date);
            } catch (Exception ex){
                date = new Date().parse("yyyy-MM-dd", movementEdit.date);
            }
            use(groovy.time.TimeCategory){
                date -= 3.hours;
            }
            movement.date = date.clearTime();
            movement.detail = movementEdit.detail;
            movement.amount = movementEdit.amount;
            movement.type = MovementType.get(movementEdit.type as Long);
            movement?.users?.clear();
            movement?.tags?.clear();
            movement?.tags?.removeAll()
            movement?.tags = null;
            movement.save(flush: true, failOnError: true);
            movementEdit.friends.each { Long userId ->
                movement.addToUsers(User.get(userId));
            }
            movementEdit.tags.each { Long tagId ->
                movement.addToTags(Tag.get(tagId));
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
            if (!movement) {
                User userLogged = loginService.getUserLogged();
                movement = new Movement();
                movement.user = userLogged;
                movement.creationDate = new Date();
                movement.lastUpdate = new Date();
                movement.date = new Date();
                movement.type = MovementType.get(1);
            }
            return [status: HttpStatus.SC_OK, response: movement.getValues()]
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error([id: movId], "MovementService.get() Exception: " + ex.getMessage());
            return [status: HttpStatus.SC_INTERNAL_SERVER_ERROR, response: [message: ex.getMessage()]]
        }
    }

}
