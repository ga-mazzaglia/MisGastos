package com.cuentasclaras

import grails.converters.JSON

class MovementType {

    String detail;
    String color;

    static mapping = {
        version false
        detail type: 'text'
    }

    static constraints = {
        detail(nullable: false, blank: false)
    }

    public Map getValues() {
        return [
                id    : id,
                detail: detail,
                color : color,
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }
}
