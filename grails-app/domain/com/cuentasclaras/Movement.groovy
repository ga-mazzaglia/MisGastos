package com.cuentasclaras

import grails.converters.JSON

class Movement {

    User user;
    Date creationDate = new Date();
    Date lastUpdate = new Date();
    Date date = new Date();
    String detail = "";
    Double amount;
    Boolean deleted = false;

    static hasMany = [tags: Tag, users: User]
    MovementType type;

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
        deleted(nullable: false)
        users(nullable: true)
    }

    public Double getTotalAmount() {
        if (users.size()) {
            return (this.amount / (users.size() + 1));
        }
        return this.amount;
    }

    public Map getValues() {
        return [
                id          : id,
                user        : user?.getValues(),
                creationDate: creationDate,
                lastUpdate  : lastUpdate,
                date        : date,
                detail      : detail,
                amount      : amount,
                deleted     : deleted,
                users       : users*.getValues(),
                tags        : tags*.getValues(),
                type        : type?.getValues(),
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }
}
