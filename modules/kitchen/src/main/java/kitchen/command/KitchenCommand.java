package kitchen.command;

import kitchen.requests.OrderPlacedRequest;
import kitchen.requests.Request;
import kitchen.requests.TabClosedRequest;
import kitchen.requests.TabCreatedRequest;

public class KitchenCommand {
    private Request request;
    public KitchenCommand(TabCreatedRequest request){
        this.request=request;
    }
    public KitchenCommand(TabClosedRequest request){
        this.request=request;
    }
    public KitchenCommand(OrderPlacedRequest request){
        this.request=request;
    }
    public TabCreatedRequest getTabCreatedRequest(){
        return (TabCreatedRequest) request;
    }
    public TabClosedRequest getTabClosedRequest(){
        return  (TabClosedRequest) request;
    }
    public OrderPlacedRequest getOrderPlacedRequest(){
        return  (OrderPlacedRequest) request;
    }

}
