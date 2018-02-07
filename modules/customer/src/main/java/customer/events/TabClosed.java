package customer.events;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TabClosed implements ParentEvent {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date=new Date();
    private String timeCreated;
    private String tabId;
    private final UUID uuid;
    private EventType eventType;
    public TabClosed(String tabId) {
        eventType= EventType.TAB_CLOSED;
        timeCreated=dateFormat.format(date);
        this.tabId=tabId;
        this.uuid = UUID.randomUUID();

    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTabId() {
        return tabId;
    }

    public String getTimeCreated() {
        return timeCreated;
    }
    public String toString(){
        return "ID: "+this.getUuid()+"  Time created: "+this.timeCreated+" message: "+ this.tabId;
    }
}
