package com.cuentasclaras

import grails.converters.JSON

class Tag {

    User user
    String detail
    Integer position
    Boolean enabled = 1

    static mapping = {
        version false
        detail type: 'text'
    }

    static constraints = {
        user(nullable: false)
        detail(nullable: false, blank: false)
        position(nullable: false)
        enabled(nullable: false)
    }

    Map getValues() {
        return [
                id     : id,
                detail : detail,
                order  : position,
                enabled: enabled,
        ]
    }

    @Override
    String toString() {
        return this.getValues() as JSON
    }

}
