package ordertracker

import ordertracker.notifications.NewBrandAndCategoryDiscount
import ordertracker.notifications.NewBrandDiscount
import ordertracker.notifications.NewCategoryDiscount
import ordertracker.notifications.NewProductDiscount

class Discount {

    static constraints = {
    }

    int product_id
    int brand_id
    
    int range_from
    int range_upto
    

    String category
    double percentage
    
    long    datebeg
    long    dateend

    def afterInsert() {

        if ( product_id != -1 )
            new NewProductDiscount(this).addNotification()

        else if ( brand_id != -1 && category != 'none' )
            new NewBrandAndCategoryDiscount(this).addNotification()

        else if ( brand_id == -1 && category != 'none' )
            new NewCategoryDiscount(this).addNotification()

        else if ( brand_id != -1 && category == 'none')
            new NewBrandDiscount(this).addNotification()

        GCMConnectorService.getInstance().push()
    }
}
