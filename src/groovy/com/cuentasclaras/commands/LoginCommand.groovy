package com.cuentasclaras.commands

import grails.converters.JSON
import grails.validation.Validateable

/**
 * Created by gmazzaglia on 20/7/16.
 */
@Validateable
class LoginCommand {
    String username;
    String password;

    static constraints = {
        username(nullable: false, blank: false)
        password(nullable: false, blank: false)
    }

    public Map getTheErrors() {
        Map result = [:];
        this.getErrors().getFieldErrors().each { e ->
            String field = e.field.capitalize()
            result << ["${field}": "invalid param"];
        }
        return result
    }

    public Map getValues() {
        return [
                username: username,
                password: password
        ]
    }

    @Override
    String toString() {
        return this.getValues() as JSON;
    }
}
