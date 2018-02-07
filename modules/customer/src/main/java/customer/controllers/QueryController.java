package customer.controllers;

import customer.aggregate.Tab;
import customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/query/")
public class QueryController {
    @Autowired
    private CustomerService customerService;
    @GetMapping(value="/findAll")
    public List<Tab> findAll(){
        return customerService.getAll();
    }


}




