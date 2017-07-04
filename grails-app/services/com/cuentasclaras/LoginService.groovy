package com.cuentasclaras

import com.cuentasclaras.commands.LoginCommand
import com.cuentasclaras.utils.Logger
import org.apache.http.HttpStatus

import java.security.MessageDigest

class LoginService {

    def grailsApplication
    def sessionService

    static private String USER_LOGGED = "userLogged"

    public User getUserLogged() {
        return (User) sessionService.getValue(USER_LOGGED);
    }

    public logout() {
        sessionService.saveValue(USER_LOGGED, null);
    }

    public Map check(LoginCommand loginCommand) {
        Map result = [:];

        User user = User.findByUsername(loginCommand.username);
        if (user) {
            if (user.passEncript == MessageDigest.getInstance("MD5").digest(loginCommand.password.bytes).encodeHex().toString()) {
                sessionService.saveValue(USER_LOGGED, user)
                result = [status: HttpStatus.SC_OK, response: [user: user]];
            } else {
                result = [status: HttpStatus.SC_BAD_REQUEST, response: [message: "Usuario y/o contraseña incorrecta!"]];
            }
        } else {
            result = [status: HttpStatus.SC_BAD_REQUEST, response: [message: "Usuario y/o contraseña incorrecta!"]];
        }

        Logger.trace([
                loginCommand: loginCommand,
                result      : result
        ], "LoginService.check() result");
        return result
    }

}
