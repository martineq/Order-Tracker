package ordertracker

class SellerLoader {
    def load() {
        new Seller(document_number:30123456, name:'José Vasconcelos', zone:'zona oeste').save
        new Seller(document_number:29876012, name:'Gabriele Picco', zone:'zona sur').save
        new Seller(document_number:27856134, name:'Mark Haddon', zone:'zona norte').save
    }
}
