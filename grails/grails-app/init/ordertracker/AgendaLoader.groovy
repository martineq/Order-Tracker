package ordertracker

class AgendaLoader {
    def load() {
        new Agenda(seller_id:1, client_id:1, day:'Lunes', time:'10:30').save
        new Agenda(seller_id:1, client_id:2, day:'Miercoles', time:'12:30').save
        new Agenda(seller_id:2, client_id:3, day:'Jueves', time:'14:00').save
    }
}
