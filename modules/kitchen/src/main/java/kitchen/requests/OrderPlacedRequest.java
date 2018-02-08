package kitchen.requests;


import kitchen.events.OrderType;

public class OrderPlacedRequest implements Request {
    private String name;
    private String tabId;
    private OrderType orderType;

    public OrderPlacedRequest(){

    }

    public OrderPlacedRequest(String name, String tabId, OrderType orderType) {
        this.name = name;
        this.tabId = tabId;
        this.orderType = orderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
