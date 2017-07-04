package com.cuentasclaras

import grails.converters.JSON

class Movement {

    User user;
    Date creationDate = new Date();
    Date lastUpdate;
    Date date;
    String detail;
    Double amount;
    MovementType type;

    static belongsTo = Tag
    static hasMany = [tags: Tag]

    static mapping = {
        version false
        detail type: 'text'
    }

    static constraints = {
        user(nullable: false)
        creationDate(nullable: false)
        lastUpdate(nullable: false)
        date(nullable: false)
        detail(nullable: false, blank: false)
        amount(nullable: false)
        type(nullable: false)
    }

    public Map getValues() {
        return [
                id          : id,
                user        : user.getValues(),
                creationDate: creationDate,
                lastUpdate  : lastUpdate,
                date        : date,
                detail      : detail,
                amount      : amount,
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }
}
