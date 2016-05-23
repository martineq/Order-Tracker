package ordertracker

class UseradminLoader {
    def load() {
        new Useradmin(email:"a@a.com",password:"password").save()
    }
}
