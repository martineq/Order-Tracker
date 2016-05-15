package ordertracker


import ordertracker.constants.Keywords


class DiscountLoader {

    
    private long generateTimeInMs(Calendar calendar, int year, int month, int day, int hour, int minutes) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, day)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        
        println "Generado desc con fecha..."
        println calendar.time 
        
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

        
        //En rango, limite superior -1 significa limite infinito.
        new Discount(product_id: -1, brand_id:3, range_from:1,range_upto:-1, category:'bebidas', percentage: 5.00,datebeg: this.generateTimeInMs(calendar, year, month, monday, 0, 0),dateend: this.generateTimeInMs(calendar, year, month, friday, 0, 00)).save()
        new Discount(product_id: -1, brand_id:7,range_from:1,range_upto:-1, category:'construccion', percentage: 5.00,datebeg: this.generateTimeInMs(calendar, year, month, tuesday, 0, 0),dateend: this.generateTimeInMs(calendar, year, month, friday, 0, 0)).save()
        new Discount(product_id: -1, brand_id:8,range_from:1,range_upto:-1, category:'none', percentage: 5.00,datebeg: this.generateTimeInMs(calendar, year, month, monday, 0, 0),dateend: this.generateTimeInMs(calendar, year, month, wednesday,0, 0)).save()
        new Discount(product_id: 2, brand_id:1,range_from:1,range_upto:-1,category:'muebles', percentage: 7.50,datebeg: this.generateTimeInMs(calendar, year, month, tuesday, 0, 0),dateend: this.generateTimeInMs(calendar, year, month, friday, 0, 0)).save()
        new Discount(product_id: 3, brand_id:2,range_from:1,range_upto:-1, category:'muebles', percentage: 3.33,datebeg: this.generateTimeInMs(calendar, year, month, monday, 0, 0),dateend: this.generateTimeInMs(calendar, year, month, wednesday, 0, 0)).save()
    }
}
