package ordertracker

class AgendaLoader {
    def load() {
        new Agenda(seller_id:1, client_id:1, day:'1', time:'19:30').save()
        new Agenda(seller_id:1, client_id:2, day:'3', time:'12:30').save()
        new Agenda(seller_id:1, client_id:4, day:'5', time:'14:30').save()
        new Agenda(seller_id:1, client_id:5, day:'5', time:'11:30').save()
        new Agenda(seller_id:1, client_id:6, day:'6', time:'15:30').save()
        new Agenda(seller_id:2, client_id:3, day:'4', time:'14:00').save()
    }
}
