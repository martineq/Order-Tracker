package ordertracker

class ClientLoader {
    def load() {
        new Client(id: 1, name: 'Juan',    address: 'Av. Pres. Figueroa Alcorta 3535', city: 'Ciudad de Buenos Aires', qrcode: 'A').save()
        new Client(id: 2, name: 'Felipe',  address: 'Juana Manso 530', city: 'Ciudad de Buenos Aires', qrcode: 'B').save()
        new Client(id: 3, name: 'Matías',  address: 'Av. del Libertador 1659',city: 'Ciudad de Buenos Aires', qrcode: 'C').save()
    }
}