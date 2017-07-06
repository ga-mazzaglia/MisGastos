package com.cuentasclaras.commands

import grails.converters.JSON
import grails.validation.Validateable

/**
 * Created by gmazzaglia on 3/7/17.
 */
@Validateable
class MovementEditCommand {
    String id
    String date
    String detail
    Double amount
    Long type
    Long[] friends
    Long[] tags

    static constraints = {
        id(nullable: true, blank: true)
        date(nullable: false, blank: false)
        detail(nullable: false, blank: false)
        amount(nullable: false)
        type(nullable: false)
        friends(nullable: true)
        tags(nullable: true)
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
                id     : id,
                date   : date,
                detail : detail,
                amount : amount,
                type   : type,
                friends: friends,
                tags   : tags,
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }

}
