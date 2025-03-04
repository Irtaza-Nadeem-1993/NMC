package com.nmc.nmc_gateway.web.rest;

import com.nmc.nmc_gateway.client.SoapClient;
import com.nmc.nmc_gateway.client.TatmeenSoapApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/nmc-gateway/v1")
public class NmcGatewayController {

    @Autowired
    private TatmeenSoapApiClient soapApiClient;

    @GetMapping("/scp/SendEPCIS")
    public ResponseEntity<String> SendEPCISPack() throws Exception {
        String requestXml =
            """
            <?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
            <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:urn="urn:sap-com:document:sap:soap:functions:mc-style">
                <soap:Body>
                    <epcis:EPCISDocument creationDate="2024-09-12T09:37:03Z" schemaVersion="1.2" xmlns:cbvmda="urn:epcglobal:cbv:mda" xmlns:epcis="urn:epcglobal:epcis:xsd:1" xmlns:sbdh="http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader" xmlns:tatmeen="http://tatmeen.ae/epcis/">
                        <EPCISBody>
                            <EventList>
                                <ObjectEvent>
                                    <action>OBSERVE</action>
                                    <bizLocation>
                                        <id>urn:epc:id:sgln:629401832465..0</id>
                                    </bizLocation>
                                    <bizStep>urn:epcglobal:cbv:bizstep:receiving</bizStep>
                                    <disposition>urn:epcglobal:cbv:disp:in_progress</disposition>
                                    <epcList>
                                        <epc>(00)357021905048582847</epc>
                                    </epcList>
                                    <eventTime>2024-09-12T09:37:03Z</eventTime>
                                    <eventTimeZoneOffset>+00:00</eventTimeZoneOffset>
                                    <readPoint>
                                        <id>urn:epc:id:sgln:629401832465..0</id>
                                    </readPoint>
                                </ObjectEvent>
                            </EventList>
                        </EPCISBody>
                        <EPCISHeader>
                            <sbdh:StandardBusinessDocumentHeader>
                                <sbdh:DocumentIdentification>
                                    <sbdh:CreationDateAndTime>2024-09-12T09:37:03Z</sbdh:CreationDateAndTime>
                                    <sbdh:InstanceIdentifier>7a3bafe1-3871-48d3-9934-41e80bc1acd9</sbdh:InstanceIdentifier>
                                    <sbdh:Standard>EPCGlobal</sbdh:Standard>
                                    <sbdh:Type>Events</sbdh:Type>
                                    <sbdh:TypeVersion>1.0</sbdh:TypeVersion>
                                </sbdh:DocumentIdentification>
                                <sbdh:HeaderVersion>1.3</sbdh:HeaderVersion>
                                <sbdh:Receiver>
                                    <sbdh:Identifier Authority="GS1">6297001273005</sbdh:Identifier>
                                </sbdh:Receiver>
                                <sbdh:Sender>
                                    <sbdh:Identifier Authority="GS1">6294018324652</sbdh:Identifier>
                                </sbdh:Sender>
                            </sbdh:StandardBusinessDocumentHeader>
                        </EPCISHeader>
                    </epcis:EPCISDocument>
                </soap:Body>
                <soap:Header></soap:Header>
            </soap:Envelope>
            """;

        String res = soapApiClient.sendSoapRequest();

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
