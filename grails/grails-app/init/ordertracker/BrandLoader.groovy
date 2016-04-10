package ordertracker

class BrandLoader {
    def load() {
        new Brand(name: 'Velasco', image:'not').save()
        new Brand(name: 'HQ', image:'not').save()
        new Brand(name: 'Coca-Cola', image:'not').save()
    }
}
