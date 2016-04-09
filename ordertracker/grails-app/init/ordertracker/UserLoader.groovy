package ordertracker

class UserLoader {
    def load() {
        new User( username: 'martin', password: '85780', token: 'token1').save()
        new User( username: 'mart',password: '86012', token: 'token2').save()
        new User( username: 'barbara',password: '92071', token: 'token3').save()
        new User( username: 'uriel',password: '93252', token: 'token4').save()
    }
}
