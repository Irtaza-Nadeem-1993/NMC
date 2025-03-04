package com.nmc.nmc_gateway.client;

import com.nmc.nmc_gateway.client.xmlBody.TatmeenReceiveCall;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TatmeenSoapApiClient {

    @Value("${tatmeen.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendSoapRequest() throws Exception {
        String url = "https://stgapim.tatmeen.ae/v1/scp/SendEPCIS"; // SOAP API URL

        String requestXmlChanged = TatmeenReceiveCall.createSOAPRequestMyWay(
            List.of("xxxxxxxx.123456789", "xxxxxxxx.123456799"),
            List.of("xxxxxxxx.123456889", "xxxxxxxx.123456890"),
            "xxxxxxxx.5555.0"
        );

        // SOAP Request XML Body
        String requestXml =
            """
            <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:urn="urn:sap-com:document:sap:soap:functions:mc-style">
                <soap:Header/>
                <soap:Body>
                    <epcis:EPCISDocument schemaVersion="1.2" creationDate="2022-01-13T15:01:26Z" xmlns:sbdh="http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader" xmlns:epcis="urn:epcglobal:epcis:xsd:1" xmlns:cbvmda="urn:epcglobal:cbv:mda" xmlns:tatmeen="http://tatmeen.ae/epcis/">
                        <EPCISHeader>
                            <sbdh:StandardBusinessDocumentHeader>
                                <sbdh:HeaderVersion>1.3</sbdh:HeaderVersion>
                                <sbdh:Sender>
                                    <sbdh:Identifier Authority="GS1">xxxxxxxxxxxxx</sbdh:Identifier>
                                </sbdh:Sender>
                                <sbdh:Receiver>
                                    <sbdh:Identifier Authority="GS1">6297001273005</sbdh:Identifier>
                                </sbdh:Receiver>
                                <sbdh:DocumentIdentification>
                                    <sbdh:Standard>EPCGlobal</sbdh:Standard>
                                    <sbdh:TypeVersion>1.0</sbdh:TypeVersion>
                                    <sbdh:InstanceIdentifier>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</sbdh:InstanceIdentifier>
                                    <sbdh:Type>Events</sbdh:Type>
                                    <sbdh:CreationDateAndTime>2022-01-13T15:01:05.000Z</sbdh:CreationDateAndTime>
                                </sbdh:DocumentIdentification>
                            </sbdh:StandardBusinessDocumentHeader>
                        </EPCISHeader>
                        <EPCISBody>
                            <EventList>
                                <AggregationEvent>
                                    <eventTime>2022-02-12T22:09:50.000Z</eventTime>
                                    <eventTimeZoneOffset>+05:30</eventTimeZoneOffset>
                                    <parentID>urn:epc:id:sscc:xxxxxxxxx.71859632</parentID>
                                    <childEPCs>
                                        <epc>urn:epc:id:sgtin:xxxxxxxx.0001.520701</epc>
                                        <epc>urn:epc:id:sgtin:xxxxxxxx.06983.1590301</epc>
                                    </childEPCs>
                                    <action>ADD</action>
                                    <bizStep>urn:epcglobal:cbv:bizstep:packing</bizStep>
                                    <readPoint>
                                        <id>urn:epc:id:sgln:xxxxxxxx.0000.0</id>
                                    </readPoint>
                                    <bizLocation>
                                        <id>urn:epc:id:sgln:xxxxxxxx.0000.0</id>
                                    </bizLocation>
                                </AggregationEvent>
                            </EventList>
                        </EPCISBody>
                    </epcis:EPCISDocument>
                </soap:Body>
            </soap:Envelope>
            """;

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set("apikey", "jEvpR2TUAwIRwfxWR445pL6yuPa2cAIB");
        headers.set(
            "Authorization",
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1OR18tbVBYM002OFR5TW9SaWpmV2NIcHUxTSIsImtpZCI6Ii1OR18tbVBYM002OFR5TW9SaWpmV2NIcHUxTSJ9.eyJhdWQiOiJtaWNyb3NvZnQ6aWRlbnRpdHlzZXJ2ZXI6YTEyMDVhOWQtNDY3My00NzA5LWEwMzAtY2ZiODk0NzMyNGY2IiwiaXNzIjoiaHR0cDovL3N0Z2lkZW50aXR5LnRhdG1lZW4uYWUvYWRmcy9zZXJ2aWNlcy90cnVzdCIsImlhdCI6MTc0MDgzMzM2NywibmJmIjoxNzQwODMzMzY3LCJleHAiOjE3NDA4MzY5NjcsImVtYWlsIjoibmZzLmF3YW5AZ21haWwuY29tIiwic3ViIjoibmZzLmF3YW5AZ21haWwuY29tIiwiZ2l2ZW5fbmFtZSI6IkZ1cWFuIiwiYXBwdHlwZSI6IkNvbmZpZGVudGlhbCIsImFwcGlkIjoiYTEyMDVhOWQtNDY3My00NzA5LWEwMzAtY2ZiODk0NzMyNGY2IiwiYXV0aG1ldGhvZCI6InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDphYzpjbGFzc2VzOlBhc3N3b3JkUHJvdGVjdGVkVHJhbnNwb3J0IiwiYXV0aF90aW1lIjoiMjAyNS0wMy0wMVQxMjo0OToyNy44MDhaIiwidmVyIjoiMS4wIn0.mWEdMFG0gQemZQhpWk8C0fhNP8ykHASwrDOzX7Ya5PAl_l7zTlDKlJY6_NmzVjyBoLgqopECCX8B6Gw0spdkaRLkE8rXo0gKgWiVM7ej7y5n2tEk71EZsLDrm_-EHg8NkszFpMOlrz5qUpP6xlL_ae326tBihTyqokfAFxnRuhmwGD4-wxgfjIl0fp1Cwv7CEqoVHZXz3p01pgTuGnhvwhKU-aPKCoRY2AEezp4rVhpENZpomSIoxdbYwNYepFVShnwItoaFRnn-qBs3oblSj54yxQbdwQeXd32VcyCLWzklvhY6OujuKKyMZ4_K2rFkya6OTzvMe37PlT9ut0h9YQ"
        );

        // Create an HTTP entity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestXmlChanged, headers);

        // Make the POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Return response body
        return response.getBody();
    }
}
