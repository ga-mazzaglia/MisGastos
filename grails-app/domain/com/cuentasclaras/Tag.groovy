package com.cuentasclaras

import grails.converters.JSON

class Tag {

    User user
    String detail

    static mapping = {
        version false
        detail type: 'text'
    }

    static constraints = {
        user(nullable: false)
        detail(nullable: false, blank: false)
    }

    public Map getValues() {
        return [
                id    : id,
                detail: detail,
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }

}
