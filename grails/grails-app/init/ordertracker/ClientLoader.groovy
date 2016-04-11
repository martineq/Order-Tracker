package ordertracker

import ordertracker.constants.ClientStates

class ClientLoader {
    def load() {
        new Client(name: 'Juan',    address: 'Av. Pres. Figueroa Alcorta 3535', city: 'Ciudad de Buenos Aires', state: ClientStates.NO_VISITADO.toString(),qrcode: 'A', email: 'a@a.com').save()
        new Client(name: 'Felipe',  address: 'Juana Manso 530', city: 'Ciudad de Buenos Aires', state: ClientStates.VISITADO.toString(), qrcode: 'B', email: 'b@b.com').save()
        new Client(name: 'Matias',  address: 'Av. del Libertador 1659',city: 'Ciudad de Buenos Aires', state: ClientStates.PENDIENTE.toString(), qrcode: 'C', email: 'c@c.com').save()
        new Client(name: 'Maria',  address: 'Av. Coronel Roca 5252',city: 'Ciudad de Buenos Aires', state: ClientStates.PENDIENTE.toString(), qrcode: 'D', email: 'd@d.com').save()
        new Client(name: 'Fernando',  address: 'Av. Sarmiento 1118',city: 'Ciudad de Buenos Aires', state: ClientStates.VISITADO.toString(), qrcode: 'E', email: 'e@e.com').save()
        new Client(name: 'Carolina',  address: 'Av. San Juan 1340',city: 'Ciudad de Buenos Aires', state: ClientStates.VISITADO.toString(), qrcode: 'F', email: 'f@f.com').save()
        new Client(name: 'Elena',  address: 'Av. Bartolome Mitre 726',city: 'Ciudad de Buenos Aires', state: ClientStates.VISITADO.toString(), qrcode: 'G', email: 'g@g.com').save()
        new Client(name: 'Roberto',  address: 'Gral Lamadrid 125',city: 'Avellaneda', state: ClientStates.VISITADO.toString(), qrcode: 'H', email: 'h@h.com').save()
    }
}