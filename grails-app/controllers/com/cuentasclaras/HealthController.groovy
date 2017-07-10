package com.cuentasclaras

import grails.converters.JSON

class HealthController {

    def ping() {
        Map result = [ping: "pong"];
        render result as JSON;
    }

}
