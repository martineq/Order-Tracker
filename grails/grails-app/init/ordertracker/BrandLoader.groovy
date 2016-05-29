package ordertracker

import ordertracker.constants.Paths

class BrandLoader {

    def path = Paths.BRANDS.toString()

    def load() {
        new Brand(name: 'Velasco'       , image: path+'1').save()
        new Brand(name: 'HQ'            , image: path+'2').save()
        new Brand(name: 'Coca-Cola'     , image: path+'3').save()
        new Brand(name: 'Philips'       , image: path+'4').save()
        new Brand(name: 'Tramontina'    , image: path+'5').save()
        new Brand(name: 'intel'         , image: path+'6').save()
        new Brand(name: 'Black & Decker', image: path+'7').save()
        new Brand(name: 'Fitbit'        , image: path+'8').save()
        new Brand(name: 'vental'        , image: path+'9').save()
        new Brand(name: 'nikon'         , image: path+'10').save()
        new Brand(name: 'givenchi'      , image: path+'11').save()
    }
    
    
    
}
