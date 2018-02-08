package customer.events;

import java.util.UUID;

public class OrderPlaced implements ParentEvent {
    private UUID uuid;
    private String name;
    private String tabId;
    private String timeCreated;
    private EventType eventType;
    private OrderStatus orderStatus;
    private OrderType orderType;
    public OrderPlaced(){

    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "OrderPlaced{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", timeCreated='" + timeCreated + '\'' +
                ", orderStatus=" + orderStatus +
                ", orderType=" + orderType +
                '}';
    }
}
