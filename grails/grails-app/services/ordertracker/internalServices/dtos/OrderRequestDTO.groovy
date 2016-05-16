package ordertracker.internalServices.dtos

import ordertracker.constants.Keywords
import ordertracker.queries.QueryException
import org.grails.web.converters.exceptions.ConverterException
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONException
import org.grails.web.json.JSONObject

class OrderRequestDTO implements DTO {
    private List<ProductRequestDTO> productRequests

    private long visit_id
    private int client_id
    private long date
    private double totalAmount

    OrderRequestDTO() {
        this.productRequests = new ArrayList<>();
    }

    boolean parse(JSONObject jsonObject) throws JSONException {
        if ( jsonObject.size() == 0 )
            throw new ConverterException()

        this.loadObjectState(jsonObject)
        this.parseArray(jsonObject)
        return true
    }

    private void loadObjectState(JSONObject jsonObject) {
        visit_id = jsonObject.getInt(Keywords.VISIT_ID.toString())
        client_id = jsonObject.getInt(Keywords.CLIENT.toString())
        date = jsonObject.getLong(Keywords.DATE_ES.toString())
        totalAmount = jsonObject.getDouble(Keywords.TOTAL_AMOUNT_ES.toString())
    }

    private boolean parseArray(JSONObject jsonObject) {
        try {
            jsonObject.getJSONArray(Keywords.LINES.toString()).each { jsonProduct ->
                ProductRequestDTO product = new ProductRequestDTO()
                product.parse(jsonProduct)
                this.productRequests.add(product)
            }
            return true
        }

        catch (JSONException e ) {
            throw new QueryException("El objeto json no respeta el protocolo: " + e.getMessage() )
        }
    }

    List<ProductRequestDTO> getProductRequests() {
        return productRequests
    }

    void setProductRequests(List<ProductRequestDTO> productRequests) {
        this.productRequests = productRequests
    }

    long getVisit_id() {
        return visit_id
    }

    void setVisit_id(long visit_id) {
        this.visit_id = visit_id
    }

    int getClient_id() {
        return client_id
    }

    void setClient_id(int client_id) {
        this.client_id = client_id
    }

    long getDate() {
        return date
    }

    void setDate(long date) {
        this.date = date
    }

    String getState() {
        return state
    }

    void setState(String state) {
        this.state = state
    }

    double getTotalAmount() {
        return totalAmount
    }

    void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount
    }

    int getSeller_id() {
        return seller_id
    }

    void setSeller_id(int seller_id) {
        this.seller_id = seller_id
    }
}
