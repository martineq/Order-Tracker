package ordertracker.database

import ordertracker.Discount
import ordertracker.Product
import ordertracker.constants.Constants
import ordertracker.constants.Keywords
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory

/**
 * Created by martin on 14/05/16.
 */
class DiscountApplier {

    private def discounts

    DiscountApplier() {
        this.discounts = null
    }

    def defineDiscounts(Product product) {

        discounts = Discount.findAllByProduct_id(product.id)

        if ( discounts != null ) return this

        discounts = Discount.findAllByBrand_id(product.brand_id)

        if ( discounts != null ) return this

        discounts = Discount.findAllByCategory(product.category)

        return this
    }

    def applyDiscounts() {
        JsonObjectBuilder jsonObject = new JsonObjectBuilder()

        if ( discounts.size() == 0 || discounts == null )
            jsonObject.addJsonableItem( new JsonObjectBuilder())

        else
            discounts.each { Discount discount ->

                def discountJsonObject = new JsonObjectBuilder()

                discountJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.ID, discount.id))
                discountJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.PERCENTAGE, discount.percentage))
                discountJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.RANGE_FROM, Constants.MIN_PRODUCTS_ITEMS))
                discountJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.RANGE_UPTO, Constants.MAX_PRODUCTS_ITEMS))

                jsonObject.addJsonableItem(discountJsonObject)
            }

        return jsonObject
    }
}
