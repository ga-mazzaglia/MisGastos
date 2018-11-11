package com.cuentasclaras

import com.cuentasclaras.commands.MovementDeleteCommand
import com.cuentasclaras.commands.MovementEditCommand
import com.cuentasclaras.commands.MovementListCommand
import com.cuentasclaras.statics.MovementsType
import com.cuentasclaras.utils.Logger
import org.apache.http.HttpStatus

class MovementService {

    def grailsApplication
    LoginService loginService;

    MovementType[] getMovementTypes() {
        return MovementType.findAll();
    }

    Tag[] getTags() {
        User userLogged = loginService.getUserLogged();
        return Tag.findAllByUser(userLogged, [sort: "order", order: "asc"]);
    }

    /**
     * Utilizado en la home
     *
     * @return
     */
    Map getDebtsByFriends() {
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

    Map getList(MovementListCommand movementListCommand) {
        List<Map> results = [];

        Map period = movementListCommand.getFilterPeriod();
        Date dateIni = period.ini;
        Date dateEnd = period.end;

        Logger.trace([
                date_ini: dateIni.format("yyyy-MM-dd HH:mm:ss"),
                date_end: dateEnd.format("yyyy-MM-dd HH:mm:ss")
        ], "Filtros de fecha");

        User userLogged = loginService.getUserLogged();
        List<Movement> movements;
        movements = Movement.findAll([sort: "date", order: "desc"]) {
            eq("deleted", false)
            user.id == userLogged.id
            if (dateIni) {
                ge("date", dateIni)
            }
            if (dateEnd) {
                le("date", dateEnd)
            }
            if (movementListCommand.search) {
                like("detail", "%${movementListCommand.search}%")
            }
            if (movementListCommand?.tags?.size()) {
                tags.id in movementListCommand.tags
            }
        }
        movements += Movement.findAll([sort: "date", order: "desc"]) {
            eq("deleted", false)
            'users' {
                eq("id", userLogged.id)
            }
            if (dateIni) {
                date >= dateIni
            }
            if (dateEnd) {
                date <= dateEnd
            }
            if (movementListCommand.search) {
                like("detail", "%${movementListCommand.search}%")
            }
            if (movementListCommand?.tags?.size()) {
                tags.id in movementListCommand.tags
            }
        }

        movements = movements.sort { it.date }.reverse();

        movements.each { Movement mov ->
            String color = "";
            Double userAmount = mov.amount;

            if (mov.type.id in (Long[]) [1]) { // gasto personal
                color = "blue";
            }
            if (mov.type.id in (Long[]) [2, 3]) { // gastos compartidos | dinero entregado
                color = mov.user.id == userLogged.id ? "red" : "green";
                userAmount = mov.user.id == userLogged.id ? (userAmount * -1) : userAmount;
            }
            if (mov.type.id in (Long[]) [4]) { // dinero recibido
                color = mov.user.id == userLogged.id ? "green" : "red";
                userAmount = mov.user.id == userLogged.id ? userAmount : (userAmount * -1);
            }

            String userToDisplay = "";
            if (mov.user.id == userLogged.id) {
                userToDisplay = mov.users*.name.join(", ")
            } else {
                userToDisplay = mov.user.name
            }

            if (mov.users.size() != 0) {
                userAmount = userAmount / (mov.users.size() + 1)
            }

            results << [
                    id           : mov.id,
                    user         : mov.user.getValues(),
                    creationDate : mov.creationDate,
                    lastUpdate   : mov.lastUpdate,
                    date         : mov.date,
                    detail       : mov.detail,
                    amount       : mov.amount,
                    userAmount   : userAmount,
                    deleted      : mov.deleted,
                    type         : mov.type.getValues(),
                    users        : mov.users*.getValues(),
                    color        : color,
                    userToDisplay: userToDisplay,
                    tags         : mov.tags.sort { it.position },
            ];
        }

        return [movements: results, period: period];
    }

    Map getStatistics(MovementListCommand movementListCommand) {
        User userLogged = loginService.getUserLogged();

        List result = []
        List<Tag> tagList = Tag.createCriteria().list {
            eq("user", userLogged)
            eq("enabled", true)
        }

        Double totalMovements = Movement.createCriteria().list {
            eq("user", userLogged)
            eq("deleted", false)
            ge("date", movementListCommand.getPeriodCustom().ini)
            le("date", movementListCommand.getPeriodCustom().end)
            'in'("type.id", (Long[]) [1, 2])
            'tags' {
                'in'("id", tagList*.id)
            }
        }*.amount.sum() ?: 0

        tagList.each { Tag tag ->
            List<Movement> userMovements = Movement.createCriteria().list {
                eq("user", userLogged)
                eq("deleted", false)
                ge("date", movementListCommand.getPeriodCustom().ini)
                le("date", movementListCommand.getPeriodCustom().end)
                'in'("type.id", (Long[]) [1, 2])
                'tags' {
                    eq("id", tag.id)
                }
            }

            Double totalTagAmount = 0
            userMovements.each {
                if (it.type.id == 2) {
                    totalTagAmount += (it.amount / (it.users.size() + 1))
                } else {
                    totalTagAmount += it.amount
                }
            }

            result << [
                    tagId  : tag.id,
                    tagName: tag.detail,
                    amount : totalTagAmount,
                    parte  : totalTagAmount ? ((totalTagAmount * 100) / totalMovements) : 0
            ]
        }

        return [tags: result.sort { -it.parte }, total: totalMovements]
    }

    Map save(MovementEditCommand movementEdit) {
        try {
            Movement movement = Movement.get(movementEdit.id);
            if (!movement) {
                movement = new Movement();
                movement.user = loginService.getUserLogged();
                Date creationDate = new Date();
                use(groovy.time.TimeCategory) {
                    creationDate += (grailsApplication.config.timeZone).hours
                }
                movement.creationDate = creationDate;
            }
            Date lastUpdate = new Date();
            use(groovy.time.TimeCategory) {
                lastUpdate += (grailsApplication.config.timeZone).hours
            }
            movement.lastUpdate = lastUpdate;
            Date date = null;
            try {
                date = new Date().parse("dd/MM/yyyy HH:mm:ss", movementEdit.date + " 00:00:00");
            } catch (Exception ex) {
                date = new Date().parse("yyyy-MM-dd", movementEdit.date + " 00:00:00");
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

    Map delete(MovementDeleteCommand movementDelete) {
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

    Map get(Long movId) {
        try {
            Movement movement = Movement.get(movId);
            if (!movement) {
                Date current = new Date();
                use(groovy.time.TimeCategory) {
                    current += (grailsApplication.config.timeZone).hours
                }
                User userLogged = loginService.getUserLogged();
                movement = new Movement();
                movement.user = userLogged;
                movement.creationDate = current;
                movement.lastUpdate = current;
                movement.date = current;
                movement.type = MovementType.get(1);
                movement.tags = movement.tags;
            }
            return [status: HttpStatus.SC_OK, response: movement.getValues()]
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error([id: movId], "MovementService.get() Exception: " + ex.getMessage());
            return [status: HttpStatus.SC_INTERNAL_SERVER_ERROR, response: [message: ex.getMessage()]]
        }
    }

    Map addTag(Long movId, Long tagId, Boolean added) {
        try {
            Movement movement = Movement.get(movId);
            if (movement) {
                Date current = new Date();
                use(groovy.time.TimeCategory) {
                    current += (grailsApplication.config.timeZone).hours
                }
                movement.lastUpdate = current;
                Tag tag = Tag.get(tagId)
                if (added)
                    movement.tags.add(tag)
                else
                    movement.tags.remove(tag)
                movement.save(flush: true, failOnError: true)
            }
            return [status: HttpStatus.SC_OK, response: movement.getValues()]
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error([id: movId], "MovementService.addTag() Exception: " + ex.getMessage());
            return [status: HttpStatus.SC_INTERNAL_SERVER_ERROR, response: [message: ex.getMessage()]]
        }
    }

}
