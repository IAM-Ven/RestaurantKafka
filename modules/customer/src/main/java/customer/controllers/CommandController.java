package customer.controllers;

import customer.command.CustomerCommand;
import customer.requests.TabClosedRequest;
import customer.requests.TabCreatedRequest;
import customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/command/")
public class CommandController {

    //@Autowired
    CustomerService customerService;
    @Autowired
    public CommandController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping(value="/openTab")
        public void openTab(@RequestBody TabCreatedRequest request){
            CustomerCommand customerCommand=new CustomerCommand(request);
            System.out.println("controler primio request: "+request.getName());
            customerService.createTabCreated(customerCommand);

        }
    @PostMapping(value="/closeTab")
        public void closeTab(@RequestBody TabClosedRequest request){
           CustomerCommand customerCommand=new CustomerCommand(request);
        System.out.println("controler primio request: "+request.getTabId());
           customerService.createTabClosed(customerCommand);
        }

}
