package com.cuentasclaras.commands

import grails.converters.JSON
import grails.validation.Validateable

/**
 * Created by gmazzaglia on 3/7/17.
 */
@Validateable
class MovementEditCommand {
    String date
    String detail
    Double amount
    Long type
    Long[] friends

    static constraints = {
        date(nullable: false, blank: false)
        detail(nullable: false, blank: false)
        amount(nullable: false)
        type(nullable: false)
        friends(nullable: false)
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
                date   : date,
                detail : detail,
                amount : amount,
                type   : type,
                friends: friends,
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }

}
