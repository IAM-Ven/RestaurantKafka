package customer.command;

import customer.requests.*;

public class CustomerCommand {
    private Request request;
    public CustomerCommand(TabCreatedRequest request){
        this.request=request;
    }
    public CustomerCommand(TabClosedRequest request){
        this.request=request;
    }
    public CustomerCommand(OrderPlacedRequest request){
        this.request=request;
    }
    public CustomerCommand(OrderAcceptedRequest request){this.request=request;}
    public CustomerCommand(OrderDeclinedRequest request){this.request=request;}
    public TabCreatedRequest getTabCreatedRequest(){
        return (TabCreatedRequest) request;
    }
    public TabClosedRequest getTabClosedRequest(){
        return  (TabClosedRequest) request;
    }
    public OrderPlacedRequest getOrderPlacedRequest(){
        return  (OrderPlacedRequest) request;
    }
    public OrderAcceptedRequest getOrderAcceptedRequest(){
        return  (OrderAcceptedRequest) request;
    }
    public OrderDeclinedRequest getOrderDeclinedRequest(){
        return  (OrderDeclinedRequest) request;
    }

}
