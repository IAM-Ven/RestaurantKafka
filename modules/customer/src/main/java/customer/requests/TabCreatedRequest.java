package customer.requests;

public class TabCreatedRequest implements Request {
    private String name;

    public TabCreatedRequest(){

    }
    public TabCreatedRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
