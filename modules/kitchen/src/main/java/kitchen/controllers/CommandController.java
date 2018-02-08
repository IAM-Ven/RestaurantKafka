package kitchen.controllers;

import kitchen.command.KitchenCommand;
import kitchen.requests.TabClosedRequest;
import kitchen.requests.TabCreatedRequest;
import kitchen.services.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/command/")
public class CommandController {


}
