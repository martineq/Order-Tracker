package ordertracker

class UserTypeLoader {

    def load() {
        (1..4).each { id -> new UserType( user_id: id, type_id: id, type: Seller.getTypeName()).save() }
    }
}
