package ordertracker.json

import ordertracker.Client
import ordertracker.ClientOrder
import ordertracker.constants.Keywords
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory

class HistoricalOrdersJsonResponse {

    private List<ClientOrder> orders

    HistoricalOrdersJsonResponse(List<ClientOrder> orders) {
        this.orders = orders
    }

    public def generateProtocolResponse() {
        if ( this.orders == null )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR,"Query failed"))

        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK,"Query succeded")).addData(new Data(this.generateData()))
    }

    public JsonObjectBuilder generateData() {
        def jsonOrders = new JsonObjectBuilder()

        this.orders.each { order ->

            try {
                def jsonOrder = new JsonObjectBuilder()
                jsonOrder.addJsonableItem(new JsonPropertyFactory(Keywords.ID, order.id))
                jsonOrder.addJsonableItem(new JsonPropertyFactory(Keywords.CLIENT, Client.findById(order.client_id).name))
                jsonOrder.addJsonableItem(new JsonPropertyFactory(Keywords.DATE_ES, order.date))
                jsonOrder.addJsonableItem(new JsonPropertyFactory(Keywords.STATE_ES, order.state))
                jsonOrder.addJsonableItem(new JsonPropertyFactory(Keywords.TOTAL_AMOUNT_ES, order.total_price))

                jsonOrders.addJsonableItem(jsonOrder)
            }

            catch ( NullPointerException e) {}
        }

        return jsonOrders
    }

}
