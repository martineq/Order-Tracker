package ordertracker

import ordertracker.constants.ClientStates

class ClientOrder {

    static constraints = {
        seller_id min:1
        client_id min:1
        state inList: [ClientStates.NO_VISITADO.toString(), ClientStates.PENDIENTE.toString(), ClientStates.VISITADO.toString()]
        date min:0
        total_price min:0.0
    }

    int seller_id
    int client_id
    String state
    long date
    double total_price

}
