package com.cuentasclaras.utils

import com.sun.org.apache.xml.internal.security.utils.Base64
import org.apache.log4j.MDC
import java.security.SecureRandom

/**
 * Created by gmazzaglia on 2/7/17.
 */
class Logger {

    static def trace = { Map args, String log ->
        args += [LEVEL: "TRACE"];
        this.printLog(args, log);
    }

    static def error = { Map args, String log ->
        args += [LEVEL: "ERROR"];
        this.printLog(args, log);
    }

    static private printLog = { Map args, String msg ->
        args += [REQUEST_ID: MDC.get("requestId")];
        String log = "";
        args.each { String key, value ->
            log += "[" + key.toLowerCase() + ": " + value + "] "
        }
        println("${log}${msg}");
    }

    /**
     * Genera un ID pseudo-unico que representa a la request en curso
     * y lo agrega al mapa de datos global de log4j.
     * (Agregar [%X{requestId}] al pattern del appender)
     * @param length
     * @return
     */
    static addRequestId(int length = 9) {
        SecureRandom rand = new SecureRandom()
        byte[] randomBytes = new byte[length]
        rand.nextBytes(randomBytes)
        def requestId = Base64.encode(randomBytes)
        MDC.put('requestId', requestId)
    }

}
