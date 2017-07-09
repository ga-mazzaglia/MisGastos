import com.cuentasclaras.utils.Logger

class BootStrap {

    def init = { servletContext ->
        Logger.trace([
                date      : new Date().format("yyyy-MM-dd HH:mm:ss"),
                scope_env : System.getenv("SCOPE"),
                scope_prop: System.getProperty("SCOPE")
        ], "BootStrap.init()");
    }
    def destroy = {
    }
}
