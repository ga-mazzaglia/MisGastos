package com.cuentasclaras

class RequestIdFilters {

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                com.cuentasclaras.utils.Logger.addRequestId();
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }

}
