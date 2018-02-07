package customer.command;

import customer.requests.Request;
import customer.requests.TabClosedRequest;
import customer.requests.TabCreatedRequest;

public class CustomerCommand {
    private Request request;
    public CustomerCommand(TabCreatedRequest request){
        this.request=request;
    }
    public CustomerCommand(TabClosedRequest request){
        this.request=request;
    }
    public TabCreatedRequest getTabCreatedRequest(){
        return (TabCreatedRequest) request;
    }
    public TabClosedRequest getTabClosedRequest(){
        return  (TabClosedRequest) request;
    }
}
