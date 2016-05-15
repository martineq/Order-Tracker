package ordertracker

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
    

}
