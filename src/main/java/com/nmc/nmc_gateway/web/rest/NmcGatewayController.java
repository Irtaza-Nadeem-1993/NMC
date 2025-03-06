package com.nmc.nmc_gateway.web.rest;

import com.nmc.nmc_gateway.client.TatmeenSoapApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nmc-gateway/v1")
public class NmcGatewayController {

    @Autowired
    private TatmeenSoapApiClient soapApiClient;

    @GetMapping("/scp/SendEPCIS")
    public ResponseEntity<String> SendEPCISPack() throws Exception {
        String res = soapApiClient.sendReceiveSoapRequest();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
