package com.cuentasclaras

import com.cuentasclaras.utils.Logger

class TestController {

    def ping() {
        Logger.trace([:], "Probando 123");

        render "pong";
    }

}
