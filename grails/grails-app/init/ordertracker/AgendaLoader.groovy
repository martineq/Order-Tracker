package ordertracker

import ordertracker.constants.Keywords

class AgendaLoader {
    
    private long generateTimeInMs(Calendar calendar, int year, int month, int day, int hour, int minutes) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, day)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        
        return calendar.getTimeInMillis()
    }

    private int getDayOfTheWeek(Calendar calendar, int day) {
        calendar.set(Calendar.DAY_OF_WEEK, day)
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    def load() {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setFirstDayOfWeek(calendar.SUNDAY)

        int year = calendar.get(Calendar.YEAR)
        int month = calendar.get(Calendar.MONTH)

        int sunday = this.getDayOfTheWeek(calendar, Calendar.SUNDAY)
        int monday = this.getDayOfTheWeek(calendar, Calendar.MONDAY)
        int tuesday = this.getDayOfTheWeek(calendar, Calendar.TUESDAY)
        int wednesday = this.getDayOfTheWeek(calendar, Calendar.WEDNESDAY)
        int thursday = this.getDayOfTheWeek(calendar, Calendar.THURSDAY)
        int friday = this.getDayOfTheWeek(calendar, Calendar.FRIDAY)
        int saturday = this.getDayOfTheWeek(calendar, Calendar.SATURDAY)

        new Agenda(1, 1, this.generateTimeInMs(calendar, year, month, monday, 19, 30)).save()
        new Agenda(1, 2, this.generateTimeInMs(calendar, year, month, wednesday, 12, 30)).save()
        new Agenda(1, 3, this.generateTimeInMs(calendar, year, month, friday, 14, 30)).save()
        new Agenda(1, 4, this.generateTimeInMs(calendar, year, month, friday, 11, 30)).save()
        new Agenda(1, 5, this.generateTimeInMs(calendar, year, month, saturday, 15, 30)).save()
        new Agenda(1, 6, this.generateTimeInMs(calendar, year, month, thursday, 14, 00)).save()
        new Agenda(1, 7, this.generateTimeInMs(calendar, year, month, tuesday, 12, 00)).save()
        new Agenda(1, 8, this.generateTimeInMs(calendar, year, month, wednesday, 11, 00)).save()
        new Agenda(1, 9, this.generateTimeInMs(calendar, year, month, thursday, 12, 00)).save()
        new Agenda(1, 10, this.generateTimeInMs(calendar, year, month, friday, 13, 00)).save()
        new Agenda(1, 11, this.generateTimeInMs(calendar, year, month, saturday, 14, 00)).save()
        new Agenda(1, 12, this.generateTimeInMs(calendar, year, month, monday, 19, 00)).save()
        new Agenda(1, 13, this.generateTimeInMs(calendar, year, month, tuesday, 16, 00)).save()
        new Agenda(1, 14, this.generateTimeInMs(calendar, year, month, wednesday, 17, 00)).save()
        new Agenda(1, 15, this.generateTimeInMs(calendar, year, month, monday, 9, 00)).save()
        new Agenda(2, 16, this.generateTimeInMs(calendar, year, month, friday, 10, 00)).save()
        new Agenda(2, 17, this.generateTimeInMs(calendar, year, month, monday, 10, 00)).save()
        new Agenda(2, 18, this.generateTimeInMs(calendar, year, month, monday, 11, 00)).save()
        new Agenda(2, 19, this.generateTimeInMs(calendar, year, month, tuesday, 14, 00)).save()
        new Agenda(2, 20, this.generateTimeInMs(calendar, year, month, wednesday, 14, 00)).save()
        new Agenda(2, 21, this.generateTimeInMs(calendar, year, month, thursday, 15, 00)).save()
        new Agenda(2, 22, this.generateTimeInMs(calendar, year, month, friday, 16, 00)).save()
        new Agenda(2, 23, this.generateTimeInMs(calendar, year, month, saturday, 17, 00)).save()
        new Agenda(2, 24, this.generateTimeInMs(calendar, year, month, monday, 12, 00)).save()
        new Agenda(2, 25, this.generateTimeInMs(calendar, year, month, tuesday, 14, 00)).save()
        new Agenda(2, 26, this.generateTimeInMs(calendar, year, month, wednesday, 11, 00)).save()
        new Agenda(2, 27, this.generateTimeInMs(calendar, year, month, thursday, 12, 00)).save()
        new Agenda(2, 28, this.generateTimeInMs(calendar, year, month, friday, 13, 00)).save()
        new Agenda(2, 29, this.generateTimeInMs(calendar, year, month, saturday, 14, 00)).save()
        new Agenda(2, 30, this.generateTimeInMs(calendar, year, month, monday, 13, 00)).save()
        new Agenda(3, 31, this.generateTimeInMs(calendar, year, month, tuesday, 16, 00)).save()
        new Agenda(3, 32, this.generateTimeInMs(calendar, year, month, wednesday, 17, 00)).save()
        new Agenda(3, 33, this.generateTimeInMs(calendar, year, month, thursday, 9, 00)).save()
        new Agenda(3, 34, this.generateTimeInMs(calendar, year, month, friday, 10, 00)).save()
        new Agenda(3, 35, this.generateTimeInMs(calendar, year, month, saturday, 11, 00)).save()
        new Agenda(3, 36, this.generateTimeInMs(calendar, year, month, monday, 14, 00)).save()
        new Agenda(3, 37, this.generateTimeInMs(calendar, year, month, tuesday, 13, 00)).save()
        new Agenda(3, 38, this.generateTimeInMs(calendar, year, month, wednesday, 14, 00)).save()
        new Agenda(3, 39, this.generateTimeInMs(calendar, year, month, thursday, 15, 00)).save()
        new Agenda(3, 40, this.generateTimeInMs(calendar, year, month, friday, 16, 00)).save()
        new Agenda(3, 41, this.generateTimeInMs(calendar, year, month, saturday, 14, 00)).save()
        new Agenda(3, 42, this.generateTimeInMs(calendar, year, month, monday, 15, 00)).save()
        new Agenda(3, 43, this.generateTimeInMs(calendar, year, month, tuesday, 10, 00)).save()
        new Agenda(3, 44, this.generateTimeInMs(calendar, year, month, wednesday, 11, 00)).save()
        new Agenda(3, 45, this.generateTimeInMs(calendar, year, month, thursday, 12, 00)).save()
        new Agenda(4, 46, this.generateTimeInMs(calendar, year, month, friday, 13, 00)).save()
        new Agenda(4, 47, this.generateTimeInMs(calendar, year, month, saturday, 14, 00)).save()
        new Agenda(4, 48, this.generateTimeInMs(calendar, year, month, monday, 16, 00)).save()
        new Agenda(4, 49, this.generateTimeInMs(calendar, year, month, tuesday, 16, 00)).save()
        new Agenda(4, 50, this.generateTimeInMs(calendar, year, month, wednesday, 17, 00)).save()
        new Agenda(4, 51, this.generateTimeInMs(calendar, year, month, thursday, 9, 00)).save()
        new Agenda(4, 52, this.generateTimeInMs(calendar, year, month, friday, 10, 00)).save()
        new Agenda(4, 53, this.generateTimeInMs(calendar, year, month, saturday, 11, 00)).save()
        new Agenda(4, 54, this.generateTimeInMs(calendar, year, month, monday, 17, 00)).save()
        new Agenda(4, 55, this.generateTimeInMs(calendar, year, month, monday, 18, 00)).save()
        new Agenda(4, 56, this.generateTimeInMs(calendar, year, month, wednesday, 14, 00)).save()
        new Agenda(4, 57, this.generateTimeInMs(calendar, year, month, thursday, 15, 00)).save()
        new Agenda(4, 58, this.generateTimeInMs(calendar, year, month, friday, 16, 00)).save()
        new Agenda(4, 1, this.generateTimeInMs(calendar, year, month, saturday, 17, 00)).save()
        new Agenda(4, 2, this.generateTimeInMs(calendar, year, month, wednesday, 14, 00)).save()
        new Agenda(5, 3, this.generateTimeInMs(calendar, year, month, wednesday, 14, 00)).save()

    }
}
