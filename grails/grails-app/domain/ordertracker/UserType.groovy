package ordertracker

class UserType {

    long    user_id
    long    type_id
    String  type

    static mapping = {
        id name:'user_id'
    }

    static constraints = { }

}
