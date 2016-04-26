package ordertracker.database

import ordertracker.ClientOrder
import ordertracker.Seller
import ordertracker.User
import ordertracker.UserType
import ordertracker.queries.QueryException
import ordertracker.util.CalendarDate

class HistoricalOrderQuery {

    private long startingDate
    private long endingDate

    public HistoricalOrderQuery(long dateFrom, long dateUpto) {
        this.startingDate = dateFrom
        this.endingDate = dateUpto
    }

    private long calculateStartingDate(long date) {
        return new CalendarDate(date).firstDayOfTheWeek()
    }

    private long calculateEndingDate(long date) {
        return new CalendarDate(date).lastDayOfTheWeek()
    }

    public List<ClientOrder> search(String username) {
        try {
            long seller = this.obtainSellerID(username)
            def query = ClientOrder.where { seller_id == seller && date >= startingDate && date < endingDate }
            return query.list(sort: 'date')
        }

        catch ( NullPointerException e) {
            throw new QueryException("No clients found for the seller who's username is: "+ sellerUsername)
        }

    }

    private long obtainSellerID(String username) {
        try {
            def user = User.findByUsername(username)
            def userType = UserType.findByUser_idAndType(user.id, Seller.getTypeName())
            return userType.type_id
        }

        catch ( NullPointerException npe ) {
            throw new QueryException("Inexistent seller")
        }
    }

}
