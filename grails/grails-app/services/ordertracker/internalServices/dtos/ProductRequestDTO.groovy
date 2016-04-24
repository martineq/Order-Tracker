package ordertracker.internalServices.dtos

import ordertracker.constants.Keywords
import org.grails.web.json.JSONObject

class ProductRequestDTO implements DTO {

    private int product_id
    private int quantity
    private int reserved_items
    private double price

    ProductRequestDTO() {
    }

    boolean parse(JSONObject jsonObject) {
        this.reserved_items = 0
        this.product_id = jsonObject.getInt(Keywords.PRODUCT.toString())
        this.quantity = jsonObject.getInt(Keywords.QUANTITY.toString())
        this.price = jsonObject.getDouble(Keywords.PRICE.toString())

        return true
    }

    int getProduct_id() {
        return product_id
    }

    void setProduct_id(int product_id) {
        this.product_id = product_id
    }

    int getQuantity() {
        return quantity
    }

    void setQuantity(int quantity) {
        this.quantity = quantity
    }

    double getPrice() {
        return price
    }

    void setPrice(double price) {
        this.price = price
    }

    int getReservedItems() {
        return this.reserved_items
    }

    void setReservedItems(int reservedItems) {
        this.reserved_items = reservedItems
    }
}
