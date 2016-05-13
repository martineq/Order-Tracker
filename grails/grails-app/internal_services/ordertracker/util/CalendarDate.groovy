package ordertracker.util

import ordertracker.constants.Keywords

class CalendarDate {

    private Calendar calendar
    private int first_day_of_the_week
    private static String[] daysES = ['Domingo','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado']
    private static String[] days = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday']

    CalendarDate(long date) {
        this.first_day_of_the_week = Calendar.SUNDAY
        calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setTimeInMillis(date)
        calendar.setFirstDayOfWeek(this.first_day_of_the_week)
    }

    CalendarDate(long date, int firstDayOfTheWeek) {
        this.first_day_of_the_week = firstDayOfTheWeek
        calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setTimeInMillis(date)
        calendar.setFirstDayOfWeek(this.first_day_of_the_week)
    }

    public long firstDayOfTheWeek() {
        calendar.set(Calendar.DAY_OF_WEEK, this.first_day_of_the_week)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.getTimeInMillis()
    }

    public long lastDayOfTheWeek() {
        calendar.set(Calendar.DAY_OF_WEEK, this.first_day_of_the_week)
        calendar.add(Calendar.DATE, +7)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.getTimeInMillis()
    }

    public static long currentDate() {
        return Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString())).getTimeInMillis()
    }

    public static long fromCurrentDate(int deviationFromCurrentDay) {
        def calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.add(Calendar.DATE, deviationFromCurrentDay)
        return calendar.getTimeInMillis()
    }

    public static String getWeekDay(int value) {
        return CalendarDate.days[value-1]
    }

    public static String getWeekDayInSpanish(int value) {
        return CalendarDate.daysES[value-1]
    }

    public static int getWeekNumberDay(String value) {
        return CalendarDate.days.findIndexOf { it == value }
    }

    public static int getWeekNumberDayInSpanish(String value) {
        return CalendarDate.daysES.findIndexOf { it == value }
    }
}