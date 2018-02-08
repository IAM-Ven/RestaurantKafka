package customer.events;

import java.util.UUID;

public class OrderAccepted implements ParentEvent {
    private UUID uuid;
    private String tabId;
    private String orderId;
    private String timeCreated;
    private EventType eventType;

    public OrderAccepted(){

    }

    public OrderAccepted(UUID uuid, String tabId, String orderId, String timeCreated, EventType eventType) {
        this.uuid = uuid;
        this.tabId = tabId;
        this.orderId = orderId;
        this.timeCreated = timeCreated;
        this.eventType = eventType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}
