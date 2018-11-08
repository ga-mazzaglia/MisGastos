package com.cuentasclaras

import grails.converters.JSON

class Tag {

    User user
    String detail
    Integer order

    static mapping = {
        version false
        detail type: 'text'
    }

    static constraints = {
        user(nullable: false)
        detail(nullable: false, blank: false)
        order(nullable: false, blank: false)
    }

    public Map getValues() {
        return [
                id    : id,
                detail: detail,
                order : order,
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }

}
