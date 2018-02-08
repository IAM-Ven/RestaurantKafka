package customer.requests;


public class OrderDeclinedRequest implements Request {
    private String tabId;
    private String orderId;

    public  OrderDeclinedRequest(){

    }
    public OrderDeclinedRequest(String tabId, String orderId) {
        this.tabId = tabId;
        this.orderId = orderId;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
