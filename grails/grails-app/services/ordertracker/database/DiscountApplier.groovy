package ordertracker.database

import ordertracker.Discount
import ordertracker.Product
import ordertracker.constants.Constants
import ordertracker.constants.Keywords
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.util.CalendarDate

/**
 * Created by martin on 14/05/16.
 */
class DiscountApplier {

    private def discounts

    DiscountApplier() {
        this.discounts = null
    }

    def defineDiscounts(Product product) {
        long currentTime = CalendarDate.currentDate()

        discounts = Discount.where{ product_id == product.id && brand_id == product.brand_id && category == product.category && datebeg < currentTime && dateend > currentTime }
        if ( discounts.size() != 0 ) return this

        discounts = Discount.where{ product_id == -1 && brand_id == product.brand_id && category == product.category && datebeg < currentTime && dateend > currentTime }
        if ( discounts.size() != 0 ) return this

        discounts = Discount.where{ product_id == -1 && brand_id == product.brand_id && category == 'none' && datebeg < currentTime && dateend > currentTime }
        if ( discounts.size() != 0 ) return this

        discounts = Discount.where{ product_id == -1 && brand_id == -1 && category == product.category && datebeg < currentTime && dateend > currentTime }
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
                discountJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.RANGE_FROM, discount.range_from))
                discountJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.RANGE_UPTO, discount.range_upto))

                jsonObject.addJsonableItem(discountJsonObject)
            }

        return jsonObject
    }
}
