package ordertracker.util

import ordertracker.constants.Keywords

class CalendarDate {

    private Calendar calendar
    private int first_day_of_the_week
    private static String[] daysES = ['Domingo','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado']
    private static String[] days = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday']

    public CalendarDate(long date) {
        this.first_day_of_the_week = Calendar.SUNDAY
        calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setTimeInMillis(date)
        calendar.setFirstDayOfWeek(this.first_day_of_the_week)
    }

    public CalendarDate(long date, int firstDayOfTheWeek) {
        this.first_day_of_the_week = firstDayOfTheWeek
        calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setTimeInMillis(date)
        calendar.setFirstDayOfWeek(this.first_day_of_the_week)
    }

    public CalendarDate() {
        calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        this.first_day_of_the_week = Calendar.SUNDAY
        calendar.setFirstDayOfWeek(this.first_day_of_the_week)
    }

    public CalendarDate(int firstDayOfTheWeek) {
        calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        this.first_day_of_the_week = firstDayOfTheWeek
        calendar.setFirstDayOfWeek(this.first_day_of_the_week)
    }

    public long startingDateOfThisWeek() {
        long currentTime = calendar.getTimeInMillis()
        int week = calendar.get(Calendar.WEEK_OF_YEAR)

        calendar.set(Calendar.WEEK_OF_YEAR, week-1)
        calendar.set(Calendar.DAY_OF_WEEK, 7)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)

        long result = calendar.getTimeInMillis()
        calendar.setTimeInMillis(currentTime)
        return result
    }

    public long finishingDateOfThisWeek() {
        long currentTime = calendar.getTimeInMillis()
        int week = calendar.get(Calendar.WEEK_OF_YEAR)

        calendar.set(Calendar.WEEK_OF_YEAR, week+1)
        calendar.setFirstDayOfWeek(Calendar.SUNDAY)
        calendar.set(Calendar.DAY_OF_WEEK, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 00)
        calendar.set(Calendar.MINUTE, 00)
        calendar.set(Calendar.SECOND, 00)

        long result = calendar.getTimeInMillis()
        calendar.setTimeInMillis(currentTime)

        return result
    }

    public static long todayFirstSecond() {
        def calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.set(Calendar.HOUR_OF_DAY, 00)
        calendar.set(Calendar.MINUTE, 00)
        calendar.set(Calendar.SECOND, 00)

        return calendar.getTimeInMillis()
    }

    public static long todayLastSecond() {
        def calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)

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