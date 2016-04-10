package ordertracker

class ClientLoader {
    def load() {
        new Client(name: 'Juan',    address: 'Av. Pres. Figueroa Alcorta 3535', city: 'Ciudad de Buenos Aires', qrcode: 'A', email: 'a@a.com').save()
        new Client(name: 'Felipe',  address: 'Juana Manso 530', city: 'Ciudad de Buenos Aires', qrcode: 'B', email: 'b@b.com').save()
        new Client(name: 'Matias',  address: 'Av. del Libertador 1659',city: 'Ciudad de Buenos Aires', qrcode: 'C', email: 'c@c.com').save()
    }
}