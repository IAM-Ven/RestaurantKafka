package customer.controllers;

import customer.command.CustomerCommand;
import customer.requests.*;
import customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/command/")
public class CommandController {

    CustomerService customerService;
    @Autowired
    public CommandController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping(value="/openTab")
        public void openTab(@RequestBody TabCreatedRequest request){
            CustomerCommand customerCommand=new CustomerCommand(request);
            customerService.createTabCreated(customerCommand);

        }
    @PostMapping(value="/closeTab")
        public void closeTab(@RequestBody TabClosedRequest request){
           CustomerCommand customerCommand=new CustomerCommand(request);
           customerService.createTabClosed(customerCommand);
        }

    @PostMapping("/placeOrder")
        public void placeOrder(@RequestBody OrderPlacedRequest request){
            CustomerCommand customerCommand=new CustomerCommand(request);
            customerService.createOrderPlaced(customerCommand);
        }
    @PostMapping("/acceptOrder")
        public void acceptOrder(@RequestBody OrderAcceptedRequest request){
        CustomerCommand customerCommand=new CustomerCommand(request);
        customerService.createOrderAccepted(customerCommand);
        }
    @PostMapping("/declineOrder")
    public void declineOrder(@RequestBody OrderDeclinedRequest request){
        CustomerCommand customerCommand=new CustomerCommand(request);
        customerService.createOrderDeclined(customerCommand);
    }
}
