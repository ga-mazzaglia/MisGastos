package com.cuentasclaras

import grails.converters.JSON

class User {

    String name;
    String username;
    String passEncript;

    static hasMany = [friends: User]

    static mapping = {
        version false
    }

    static constraints = {
        name(nullable: false, blank: false)
        username(nullable: false, blank: false)
        passEncript(nullable: false, blank: false)
        friends(nullable: true)
    }

    public Map getValues() {
        return [
                id         : id,
                name       : name,
                username   : username,
                passEncript: passEncript,
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }

}
