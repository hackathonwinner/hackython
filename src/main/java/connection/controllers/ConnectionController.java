package connection.controllers;

import connection.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
public class ConnectionController
{
    @Autowired
    ConnectionService connectionService;

    @RequestMapping(value = "/connected")
    @ResponseBody
    public String connectedQuery( @RequestParam("origin") String origin,
                                  @RequestParam("destination") String destination) throws IOException
    {
        connectionService.createConnections();
        if (connectionService.isConnectedRoad(origin, destination))
        {
            return "yes";
        }
        return "no";
    }

    @RequestMapping(value = "/transaction")
    @ResponseBody
    public String transactionQuery( @RequestParam("price") String price) throws IOException
    {
        //call service to make a call to debit or credit
        String payStore = connectionService.payStore(price);
        return payStore;
    }
}
