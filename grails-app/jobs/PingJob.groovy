import com.cuentasclaras.builders.RestBuilder
import com.cuentasclaras.utils.Logger
import grails.util.Environment

/**
 * Created by gmazzaglia on 10/7/17.
 */
class PingJob {

    def grailsApplication

    static triggers = {
        if (Environment.current != Environment.DEVELOPMENT) {
            cron name: 'PingJob', cronExpression: "0 */1 * * * ?"
        }
    }

    def group = "MyGroup"
    def description = "Example job with Simple Trigger"

    def execute() {
        Date date = new Date();
        use(groovy.time.TimeCategory) {
            date += (grailsApplication.config.timeZone).hours
        }

        try {
            RestBuilder builder = new RestBuilder();
            Map result = builder.withUrl("http://gamatest.herokuapp.com/ping").build().doGet();
            Logger.addRequestId();
            Logger.trace([
                    date  : date.format("yyyy-MM-dd HH:mm:ss"),
                    result: result
            ], "Job pong!");
        } catch (Exception ex) {
            Logger.error([
                    date   : date.format("yyyy-MM-dd HH:mm:ss"),
                    result : null,
                    message: ex.getMessage()
            ], "Exception to execute ping!");
        }
    }
}
