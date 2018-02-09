package customer.events;

import java.util.UUID;

public class OrderSold {
    private UUID uuid;
    private String timeCreated;
    private EventType eventType;
    private String orderId;
    private String tabId;

    public OrderSold(){

    }
    public OrderSold(UUID uuid, String timeCreated, EventType eventType, String orderId, String tabId) {
        this.uuid = uuid;
        this.timeCreated = timeCreated;
        this.eventType = eventType;
        this.orderId = orderId;
        this.tabId = tabId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }
}
