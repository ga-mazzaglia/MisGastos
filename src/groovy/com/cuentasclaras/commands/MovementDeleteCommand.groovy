package com.cuentasclaras.commands

import grails.converters.JSON
import grails.validation.Validateable

/**
 * Created by gmazzaglia on 3/7/17.
 */
@Validateable
class MovementDeleteCommand {
    Long id

    static constraints = {
        id(nullable: false)
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
                id: id,
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }

}
