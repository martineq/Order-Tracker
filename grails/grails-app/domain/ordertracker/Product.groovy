package ordertracker

import ordertracker.notifications.NewProduct

class Product {

    static constraints = {
        stock min: 0
        price min: new Double(0.0)
    }

    String name
    int brand_id
    String image
    String category
    String characteristic
    int stock
    Double price
    String state

    def afterInsert() {
        new NewProduct(this).addNotification()
        GCMConnectorService.getInstance().push()
    }
}
