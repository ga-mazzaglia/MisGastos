package com.cuentasclaras

import com.cuentasclaras.utils.Logger
import org.codehaus.groovy.grails.web.util.WebUtils

class SessionService {

    def saveValue(String key, def value) {
        try {
            def session = WebUtils.retrieveGrailsWebRequest().getSession();
            session."$key" = value;
            return session."$key" == value;
        } catch (Exception ex) {
            Logger.error([
                    exception: ex.getMessage(),
                    key      : key,
                    value    : value,
            ], "SessionService.saveValue() Exception");
            return false;
        }
    }

    def getValue(String key) {
        try {
            def session = WebUtils.retrieveGrailsWebRequest().getSession();
            return session."$key"
        } catch (Exception ex) {
            Logger.error([
                    exception: ex.getMessage(),
                    key      : key,
            ], "SessionService.getValue() Exception");
            return null;
        }
    }

}
