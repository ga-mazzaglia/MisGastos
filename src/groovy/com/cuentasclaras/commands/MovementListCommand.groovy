package com.cuentasclaras.commands

import grails.converters.JSON
import grails.validation.Validateable

/**
 * Created by gmazzaglia on 3/7/17.
 */
@Validateable
class MovementListCommand {

    static String PERIOD_TODAY = "today"
    static String PERIOD_THISWEEK = "thisweek"
    static String PERIOD_THISMONTH = "thismonth"
    static String PERIOD_LASTMONTH = "lastmonth"

    String search
    String filter_perdiod
    String dateIni
    String dateEnd

    static constraints = {
        search(nullable: true, blank: true)
        filter_perdiod(nullable: true, blank: true, inList: [PERIOD_TODAY, PERIOD_THISWEEK, PERIOD_THISMONTH, PERIOD_LASTMONTH])
        dateIni(nullable: true, blank: true)
        dateEnd(nullable: true, blank: true)
    }

    public Map getPeriodToday() {
        Date ini = new Date().clearTime();
        Date end = new Date().clearTime();
        use(groovy.time.TimeCategory) {
            end = end + 23.hours + 59.minutes + 59.seconds
        }
        return [ini: ini, end: end];
    }

    public Map getPeriodThisWeek() {
        Date ini = null;
        Date end = null;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ini = c.getTime().clearTime();
        use(groovy.time.TimeCategory) {
            end = ini + 6.days + 23.hours + 59.minutes + 59.seconds
        }
        return [ini: ini, end: end];
    }

    public Map getPeriodThisMonth() {
        Date ini = null;
        Date end = null;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        ini = c.getTime().clearTime();
        use(groovy.time.TimeCategory) {
            end = ini + 1.month - 1.day + 23.hours + 59.minutes + 59.seconds
        }
        return [ini: ini, end: end];
    }

    public Map getPeriodLastMonth() {
        Date ini = null;
        Date end = null;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        ini = c.getTime().clearTime();
        use(groovy.time.TimeCategory) {
            ini = ini - 1.month
        }
        use(groovy.time.TimeCategory) {
            end = ini + 1.month - 1.day + 23.hours + 59.minutes + 59.seconds
        }
        return [ini: ini, end: end];
    }

    public Map getPeriodCustom() {
        Date ini = null;
        Date end = null;

        try {
            if (this.dateIni) {
                ini = new Date().parse("dd/MM/yyyy", this.dateIni).clearTime()
            } else {
                ini = new Date().parse("dd/MM/yyyy", new Date().format("dd/MM/yyyy")).clearTime()
            }
            if (this.dateEnd) {
                end = new Date().parse("dd/MM/yyyy HH:mm:ss", this.dateEnd + " 23:59:59")
            } else {
                use(groovy.time.TimeCategory) {
                    end = ini + 23.hours + 59.minutes + 59.seconds
                }
            }
        } catch (Exception) {
            if (this.dateIni) {
                ini = new Date().parse("yyyy-MM-dd", this.dateIni).clearTime()
            } else {
                ini = new Date().parse("yyyy-MM-dd", new Date().format("yyyy-MM-dd")).clearTime()
            }
            if (this.dateEnd) {
                end = new Date().parse("yyyy-MM-dd HH:mm:ss", this.dateEnd + " 23:59:59")
            } else {
                use(groovy.time.TimeCategory) {
                    end = ini + 23.hours + 59.minutes + 59.seconds
                }
            }
        }

        return [ini: ini, end: end];
    }

    public boolean isFilterPeriodToday() {
        return this.filter_perdiod == PERIOD_TODAY;
    }

    public boolean isFilterPeriodThisWeek() {
        return this.filter_perdiod == PERIOD_THISWEEK;
    }

    public boolean isFilterPeriodThisMonth() {
        return this.filter_perdiod == PERIOD_THISMONTH;
    }

    public boolean isFilterPeriodLastMonth() {
        return this.filter_perdiod == PERIOD_LASTMONTH;
    }

    public Map getFilterPeriod() {
        if (this.dateIni || this.dateEnd) {
            return this.getPeriodCustom();
        }
        if (this.isFilterPeriodThisWeek()) {
            return this.getPeriodThisWeek();
        }
        if (this.isFilterPeriodThisMonth()) {
            return this.getPeriodThisMonth();
        }
        if (this.isFilterPeriodLastMonth()) {
            return this.getPeriodLastMonth();
        }
        return this.getPeriodToday();
    }

    public Map getTheErrors() {
        Map result = [:];
        this.getErrors().getFieldErrors().each { e ->
            String field = e.field.capitalize()
            result << ["${field}": "invalid param"];
        }
        return result
    }

    public Map getValues() {
        return [
                search        : search,
                filter_perdiod: filter_perdiod,
                dateIni       : dateIni,
                dateEnd       : dateEnd,
        ];
    }

    @Override
    public String toString() {
        return this.getValues() as JSON;
    }

}
