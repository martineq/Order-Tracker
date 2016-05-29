package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium
import ordertracker.util.CalendarDate

class WeeklyScheduleService implements Queryingly{

    private String sellerUsername
    private List<Agenda> weeklySchedule
    private long firstScheduledDay
    private long lastScheduledDay

    WeeklyScheduleService() {
        this.sellerUsername = null
        this.weeklySchedule = null
        this.defineScheduleWeek()
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) != 0 )
            throw new QueryException("Invalid HTTP request method: must be GET")

        this.sellerUsername = requester.getProperty(Keywords.USERNAME)
        return this.sellerUsername != null
    }

    @Override
    def generateQuery() {
        try {
            def user = User.findByUsername(this.sellerUsername)
            def userType = UserType.findByUser_idAndType(user.id, Seller.getTypeName())
            def query = Agenda.where { seller_id == userType.type_id && date > firstScheduledDay && date < lastScheduledDay }

            weeklySchedule = query.list(sort:'date')

            return true
        }

        catch ( NullPointerException e) {
            throw new QueryException("No clients found for the seller who's username is: "+ sellerUsername)
        }
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        try {
            return new ProtocolJsonBuilder().addStatus(new Status(Result.OK, "Seller schedule retrieved")).addData(this.generateData())
        }

        catch( NullPointerException npe ){
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, "Server error"))
        }
    }

    private Data generateData() {
        JsonObjectBuilder jsonDataObject = new JsonObjectBuilder()

        if ( weeklySchedule != null )
            weeklySchedule.each { item -> jsonDataObject.addJsonableItem(this.generateClientJsonObject(item)) }

        else
            jsonDataObject.addJsonableItem(new JsonObjectBuilder())

        return new Data(jsonDataObject)
    }

    private JsonObjectBuilder generateClientJsonObject(Agenda item) {
        Client client = Client.findById(item.client_id)

        JsonObjectBuilder jsonClientObject = new JsonObjectBuilder()
        jsonClientObject.addJsonableItem(new JsonPropertyFactory(Keywords.ID, client.id));
        jsonClientObject.addJsonableItem(new JsonPropertyFactory(Keywords.VISIT_ID, item.id));
        jsonClientObject.addJsonableItem(new JsonPropertyFactory(Keywords.NAME, (String) client.name));
        jsonClientObject.addJsonableItem(new JsonPropertyFactory(Keywords.ADDRESS, client.address));
        jsonClientObject.addJsonableItem(new JsonPropertyFactory(Keywords.CITY, client.city));
        jsonClientObject.addJsonableItem(new JsonPropertyFactory(Keywords.STATE, item.state));
        jsonClientObject.addJsonableItem(new JsonPropertyFactory(Keywords.DATE, item.date));

        return jsonClientObject
    }

    private def defineScheduleWeek() {
        def calendar = new CalendarDate()
        this.firstScheduledDay = calendar.startingDateOfThisWeek()
        this.lastScheduledDay = calendar.finishingDateOfThisWeek()
    }
}
