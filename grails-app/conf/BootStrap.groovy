import com.cuentasclaras.utils.Logger

class BootStrap {

    def grailsApplication
    
    def init = { servletContext ->
        Logger.trace([
                date      : new Date().format("yyyy-MM-dd HH:mm:ss"),
                time_zone : grailsApplication.config.timeZone,
                scope_env : System.getenv("SCOPE"),
                scope_prop: System.getProperty("SCOPE")
        ], "BootStrap.init()");
    }
    def destroy = {
    }
}
