package ordertracker.util

import ordertracker.constants.Keywords

class CalendarDate {

    private Calendar calendar
    private int first_day_of_the_week

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
}
