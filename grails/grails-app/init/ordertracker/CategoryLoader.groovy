package ordertracker

import ordertracker.constants.Paths

class CategoryLoader {
    def load() {
            new Category(name: 'bebidas').save()
            new Category(name: 'construccion').save()
            new Category(name: 'electronica').save()
            new Category(name: 'muebles').save()
            new Category(name: 'cuidado personal').save()
            new Category(name: 'cocina').save()
            new Category(name: 'iluminacion').save()
    }
    
}
