package kitchen.requests;

public class TabClosedRequest implements Request{
    private String tabId;

    public TabClosedRequest(){

    }
    public TabClosedRequest(String tabId) {
        this.tabId = tabId;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }
}
