package ordertracker

import ordertracker.constants.OrderStates

class ClientOrder {

    static constraints = {
        seller_id min:1
        client_id min:1
        state inList: [OrderStates.SOLICITADO.toString(), OrderStates.CANCELADO.toString(), OrderStates.DESPACHADO.toString()]
        date min: (long) 0
        total_price min: (double) 0.0
    }

    int seller_id
    int client_id
    String state
    long date
    double total_price
    String sellername
    String clientname

}
