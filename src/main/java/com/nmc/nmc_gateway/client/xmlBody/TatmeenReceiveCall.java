package com.nmc.nmc_gateway.client.xmlBody;

import java.io.StringWriter;
import java.util.List;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TatmeenReceiveCall {

    public static String createSOAPRequestMyWay(List<String> ssccList, List<String> gtinList, String gln) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Create XML Declaration
            doc.setXmlStandalone(false);

            // Create SOAP Envelope
            Element envelope = doc.createElementNS("http://www.w3.org/2003/05/soap-envelope", "soap:Envelope");
            envelope.setAttribute("xmlns:urn", "urn:sap-com:document:sap:soap:functions:mc-style");
            doc.appendChild(envelope);

            // Add Header
            Element header = doc.createElement("soap:Header");
            envelope.appendChild(header);

            // Add Body
            Element body = doc.createElement("soap:Body");
            envelope.appendChild(body);

            // Create EPCISDocument
            Element epcisDoc = doc.createElement("epcis:EPCISDocument");
            epcisDoc.setAttribute("creationDate", "2022-01-10T18:51:26Z");
            epcisDoc.setAttribute("schemaVersion", "1.2");
            epcisDoc.setAttribute("xmlns:cbvmda", "urn:epcglobal:cbv:mda");
            epcisDoc.setAttribute("xmlns:epcis", "urn:epcglobal:epcis:xsd:1");
            epcisDoc.setAttribute("xmlns:sbdh", "http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader");
            epcisDoc.setAttribute("xmlns:tatmeen", "http://tatmeen.ae/epcis/");
            body.appendChild(epcisDoc);

            // Create EPCISHeader
            Element epcisHeader = doc.createElement("EPCISHeader");
            epcisDoc.appendChild(epcisHeader);

            Element sbdh = doc.createElement("sbdh:StandardBusinessDocumentHeader");
            epcisHeader.appendChild(sbdh);

            Element docId = doc.createElement("sbdh:DocumentIdentification");
            sbdh.appendChild(docId);

            Element creationDate = doc.createElement("sbdh:CreationDateAndTime");
            creationDate.setTextContent("2022-01-13T11:53:31.000Z");
            docId.appendChild(creationDate);

            Element instanceId = doc.createElement("sbdh:InstanceIdentifier");
            instanceId.setTextContent("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            docId.appendChild(instanceId);

            Element standard = doc.createElement("sbdh:Standard");
            standard.setTextContent("EPCGlobal");
            docId.appendChild(standard);

            Element type = doc.createElement("sbdh:Type");
            type.setTextContent("Events");
            docId.appendChild(type);

            Element typeVersion = doc.createElement("sbdh:TypeVersion");
            typeVersion.setTextContent("1.0");
            docId.appendChild(typeVersion);

            Element headerVersion = doc.createElement("sbdh:HeaderVersion");
            headerVersion.setTextContent("1.3");
            sbdh.appendChild(headerVersion);

            Element receiver = doc.createElement("sbdh:Receiver");
            Element receiverId = doc.createElement("sbdh:Identifier");
            receiverId.setAttribute("Authority", "GS1");
            receiverId.setTextContent("xxxxxxxxxxxxx");
            receiver.appendChild(receiverId);
            sbdh.appendChild(receiver);

            Element sender = doc.createElement("sbdh:Sender");
            Element senderId = doc.createElement("sbdh:Identifier");
            senderId.setAttribute("Authority", "GS1");
            senderId.setTextContent("xxxxxxxxxxxxx");
            sender.appendChild(senderId);
            sbdh.appendChild(sender);

            // Create EPCISBody
            Element epcisBody = doc.createElement("EPCISBody");
            epcisDoc.appendChild(epcisBody);

            Element eventList = doc.createElement("EventList");
            epcisBody.appendChild(eventList);

            Element objectEvent = doc.createElement("ObjectEvent");
            eventList.appendChild(objectEvent);

            Element eventTime = doc.createElement("eventTime");
            eventTime.setTextContent("2022-03-28T14:00:31.000Z");
            objectEvent.appendChild(eventTime);

            Element timeZoneOffset = doc.createElement("eventTimeZoneOffset");
            timeZoneOffset.setTextContent("+05:30");
            objectEvent.appendChild(timeZoneOffset);

            Element epcList = doc.createElement("epcList");
            for (String sscc : ssccList) {
                Element epc = doc.createElement("epc");
                epc.setTextContent("urn:epc:id:sscc:" + sscc);
                epcList.appendChild(epc);
            }
            for (String gtin : gtinList) {
                Element epc = doc.createElement("epc");
                epc.setTextContent("urn:epc:id:gtin:" + gtin);
                epcList.appendChild(epc);
            }
            objectEvent.appendChild(epcList);

            Element action = doc.createElement("action");
            action.setTextContent("OBSERVE");
            objectEvent.appendChild(action);

            Element bizStep = doc.createElement("bizStep");
            bizStep.setTextContent("urn:epcglobal:cbv:bizstep:receiving");
            objectEvent.appendChild(bizStep);

            Element disposition = doc.createElement("disposition");
            disposition.setTextContent("urn:epcglobal:cbv:disp:in_progress");
            objectEvent.appendChild(disposition);

            Element readPoint = doc.createElement("readPoint");
            Element readId = doc.createElement("id");
            readId.setTextContent("urn:epc:id:sgln:" + gln);
            readPoint.appendChild(readId);
            objectEvent.appendChild(readPoint);

            Element bizLocation = doc.createElement("bizLocation");
            Element bizId = doc.createElement("id");
            bizId.setTextContent("urn:epc:id:sgln:" + gln);
            bizLocation.appendChild(bizId);
            objectEvent.appendChild(bizLocation);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            return writer.toString();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createSOAPRequest(List<String> ssccList, List<String> gtinList, String gln) throws Exception {
        // Create a SOAP message
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Create SOAP envelope
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
        envelope.addNamespaceDeclaration("cbvmda", "urn:epcglobal:cbv:mda");
        envelope.addNamespaceDeclaration("epcis", "urn:epcglobal:epcis:xsd:1");
        envelope.addNamespaceDeclaration("sbdh", "http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader");
        envelope.addNamespaceDeclaration("tatmeen", "http://tatmeen.ae/epcis/");
        envelope.addNamespaceDeclaration("urn", "urn:sap-com:document:sap:soap:functions:mc-style");

        // Build the SOAP body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement epcisDocument = soapBody.addChildElement("EPCISDocument", "epcis");
        epcisDocument.addAttribute(envelope.createName("schemaVersion"), "1.2");
        epcisDocument.addAttribute(envelope.createName("creationDate"), "2022-01-10T18:51:26Z");

        // Add StandardBusinessDocumentHeader
        SOAPElement standardBusinessDocumentHeader = epcisDocument.addChildElement("StandardBusinessDocumentHeader", "sbdh");
        standardBusinessDocumentHeader.addChildElement("HeaderVersion").addTextNode("1.3");
        SOAPElement sender = standardBusinessDocumentHeader.addChildElement("Sender");
        sender.addChildElement("Identifier").addAttribute(envelope.createName("Authority"), "GS1").addTextNode("xxxxxxxxxxxxx");
        SOAPElement receiver = standardBusinessDocumentHeader.addChildElement("Receiver");
        receiver.addChildElement("Identifier").addAttribute(envelope.createName("Authority"), "GS1").addTextNode("xxxxxxxxxxxxx");
        SOAPElement documentIdentification = standardBusinessDocumentHeader.addChildElement("DocumentIdentification");
        documentIdentification.addChildElement("Standard").addTextNode("EPCGlobal");
        documentIdentification.addChildElement("TypeVersion").addTextNode("1.0");
        documentIdentification.addChildElement("InstanceIdentifier").addTextNode(UUID.randomUUID().toString());
        documentIdentification.addChildElement("Type").addTextNode("Events");
        documentIdentification.addChildElement("CreationDateAndTime").addTextNode("2022-01-13T11:53:31.000Z");

        // Add EPCISBody
        SOAPElement epcisBody = epcisDocument.addChildElement("EPCISBody");
        SOAPElement eventList = epcisBody.addChildElement("EventList");

        // Add ObjectEvent
        SOAPElement objectEvent = eventList.addChildElement("ObjectEvent");
        objectEvent.addChildElement("eventTime").addTextNode("2022-03-28T14:00:31.000Z");
        objectEvent.addChildElement("eventTimeZoneOffset").addTextNode("+05:30");

        // Add EPC list
        SOAPElement epcList = objectEvent.addChildElement("epcList");
        for (String sscc : ssccList) {
            epcList.addChildElement("epc").addTextNode("urn:epc:id:sscc:" + sscc);
        }
        for (String gtin : gtinList) {
            epcList.addChildElement("epc").addTextNode("urn:epc:id:gtin:" + gtin);
        }

        objectEvent.addChildElement("action").addTextNode("OBSERVE");
        objectEvent.addChildElement("bizStep").addTextNode("urn:epcglobal:cbv:bizstep:receiving");
        objectEvent.addChildElement("disposition").addTextNode("urn:epcglobal:cbv:disp:in_progress");

        // Add readPoint and bizLocation
        SOAPElement readPoint = objectEvent.addChildElement("readPoint");
        readPoint.addChildElement("id").addTextNode("urn:epc:id:sgln:" + gln);
        SOAPElement bizLocation = objectEvent.addChildElement("bizLocation");
        bizLocation.addChildElement("id").addTextNode("urn:epc:id:sgln:" + gln);

        // Save the message changes
        soapMessage.saveChanges();

        return getSOAPMessageAsString(soapMessage);
    }

    public static String createSOAPRequestNewOne() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Create XML Declaration
        doc.setXmlStandalone(false);

        // Create SOAP Envelope
        Element envelope = doc.createElementNS("http://www.w3.org/2003/05/soap-envelope", "soap:Envelope");
        envelope.setAttribute("xmlns:urn", "urn:sap-com:document:sap:soap:functions:mc-style");
        doc.appendChild(envelope);

        // Add Header
        Element header = doc.createElement("soap:Header");
        envelope.appendChild(header);

        // Add Body
        Element body = doc.createElement("soap:Body");
        envelope.appendChild(body);

        // Create EPCISDocument
        Element epcisDoc = doc.createElement("epcis:EPCISDocument");
        epcisDoc.setAttribute("creationDate", "2024-09-12T09:37:03Z");
        epcisDoc.setAttribute("schemaVersion", "1.2");
        epcisDoc.setAttribute("xmlns:cbvmda", "urn:epcglobal:cbv:mda");
        epcisDoc.setAttribute("xmlns:epcis", "urn:epcglobal:epcis:xsd:1");
        epcisDoc.setAttribute("xmlns:sbdh", "http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader");
        epcisDoc.setAttribute("xmlns:tatmeen", "http://tatmeen.ae/epcis/");
        body.appendChild(epcisDoc);

        // Create EPCISBody
        Element epcisBody = doc.createElement("EPCISBody");
        epcisDoc.appendChild(epcisBody);

        Element eventList = doc.createElement("EventList");
        epcisBody.appendChild(eventList);

        Element objectEvent = doc.createElement("ObjectEvent");
        eventList.appendChild(objectEvent);

        Element action = doc.createElement("action");
        action.setTextContent("OBSERVE");
        objectEvent.appendChild(action);

        Element bizLocation = doc.createElement("bizLocation");
        Element bizId = doc.createElement("id");
        bizId.setTextContent("urn:epc:id:sgln:629401832465..0");
        bizLocation.appendChild(bizId);
        objectEvent.appendChild(bizLocation);

        Element bizStep = doc.createElement("bizStep");
        bizStep.setTextContent("urn:epcglobal:cbv:bizstep:receiving");
        objectEvent.appendChild(bizStep);

        Element disposition = doc.createElement("disposition");
        disposition.setTextContent("urn:epcglobal:cbv:disp:in_progress");
        objectEvent.appendChild(disposition);

        Element epcList = doc.createElement("epcList");
        Element epc = doc.createElement("epc");
        epc.setTextContent("(00)357021905048582847");
        epcList.appendChild(epc);
        objectEvent.appendChild(epcList);

        Element eventTime = doc.createElement("eventTime");
        eventTime.setTextContent("2024-09-12T09:37:03Z");
        objectEvent.appendChild(eventTime);

        Element timeZoneOffset = doc.createElement("eventTimeZoneOffset");
        timeZoneOffset.setTextContent("+00:00");
        objectEvent.appendChild(timeZoneOffset);

        Element readPoint = doc.createElement("readPoint");
        Element readId = doc.createElement("id");
        readId.setTextContent("urn:epc:id:sgln:629401832465..0");
        readPoint.appendChild(readId);
        objectEvent.appendChild(readPoint);

        // Create EPCISHeader
        Element epcisHeader = doc.createElement("EPCISHeader");
        epcisDoc.appendChild(epcisHeader);

        Element sbdh = doc.createElement("sbdh:StandardBusinessDocumentHeader");
        epcisHeader.appendChild(sbdh);

        Element docId = doc.createElement("sbdh:DocumentIdentification");
        sbdh.appendChild(docId);

        Element creationDate = doc.createElement("sbdh:CreationDateAndTime");
        creationDate.setTextContent("2024-09-12T09:37:03Z");
        docId.appendChild(creationDate);

        Element instanceId = doc.createElement("sbdh:InstanceIdentifier");
        instanceId.setTextContent("7a3bafe1-3871-48d3-9934-41e80bc1acd9");
        docId.appendChild(instanceId);

        Element standard = doc.createElement("sbdh:Standard");
        standard.setTextContent("EPCGlobal");
        docId.appendChild(standard);

        Element type = doc.createElement("sbdh:Type");
        type.setTextContent("Events");
        docId.appendChild(type);

        Element typeVersion = doc.createElement("sbdh:TypeVersion");
        typeVersion.setTextContent("1.0");
        docId.appendChild(typeVersion);

        Element headerVersion = doc.createElement("sbdh:HeaderVersion");
        headerVersion.setTextContent("1.3");
        sbdh.appendChild(headerVersion);

        Element receiver = doc.createElement("sbdh:Receiver");
        Element receiverId = doc.createElement("sbdh:Identifier");
        receiverId.setAttribute("Authority", "GS1");
        receiverId.setTextContent("6297001273005");
        receiver.appendChild(receiverId);
        sbdh.appendChild(receiver);

        Element sender = doc.createElement("sbdh:Sender");
        Element senderId = doc.createElement("sbdh:Identifier");
        senderId.setAttribute("Authority", "GS1");
        senderId.setTextContent("6294018324652");
        sender.appendChild(senderId);
        sbdh.appendChild(sender);

        // Convert to String
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    public static String createSOAPRequest() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Create XML Declaration
        //doc.setXmlStandalone(false);

        // Create SOAP Envelope
        Element envelope = doc.createElementNS("http://www.w3.org/2003/05/soap-envelope", "soap:Envelope");
        envelope.setAttribute("xmlns:urn", "urn:sap-com:document:sap:soap:functions:mc-style");
        doc.appendChild(envelope);

        // Add Header
        Element header = doc.createElement("soap:Header");
        envelope.appendChild(header);

        // Add Body
        Element body = doc.createElement("soap:Body");
        envelope.appendChild(body);

        // Create EPCISDocument
        Element epcisDoc = doc.createElement("epcis:EPCISDocument");
        epcisDoc.setAttribute("schemaVersion", "1.2");
        epcisDoc.setAttribute("creationDate", "2022-01-10T18:51:26Z");
        epcisDoc.setAttribute("xmlns:sbdh", "http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader");
        epcisDoc.setAttribute("xmlns:epcis", "urn:epcglobal:epcis:xsd:1");
        epcisDoc.setAttribute("xmlns:cbvmda", "urn:epcglobal:cbv:mda");
        epcisDoc.setAttribute("xmlns:tatmeen", "http://tatmeen.ae/epcis/");
        body.appendChild(epcisDoc);

        // Create EPCISHeader
        Element epcisHeader = doc.createElement("EPCISHeader");
        epcisDoc.appendChild(epcisHeader);

        Element sbdh = doc.createElement("sbdh:StandardBusinessDocumentHeader");
        epcisHeader.appendChild(sbdh);

        Element headerVersion = doc.createElement("sbdh:HeaderVersion");
        headerVersion.setTextContent("1.3");
        sbdh.appendChild(headerVersion);

        Element sender = doc.createElement("sbdh:Sender");
        Element senderId = doc.createElement("sbdh:Identifier");
        senderId.setAttribute("Authority", "GS1");
        senderId.setTextContent("xxxxxxxxxxxxx");
        sender.appendChild(senderId);
        sbdh.appendChild(sender);

        Element receiver = doc.createElement("sbdh:Receiver");
        Element receiverId = doc.createElement("sbdh:Identifier");
        receiverId.setAttribute("Authority", "GS1");
        receiverId.setTextContent("xxxxxxxxxxxxx");
        receiver.appendChild(receiverId);
        sbdh.appendChild(receiver);

        Element docId = doc.createElement("sbdh:DocumentIdentification");
        Element standard = doc.createElement("sbdh:Standard");
        standard.setTextContent("EPCGlobal");
        docId.appendChild(standard);

        Element typeVersion = doc.createElement("sbdh:TypeVersion");
        typeVersion.setTextContent("1.0");
        docId.appendChild(typeVersion);

        Element instanceId = doc.createElement("sbdh:InstanceIdentifier");
        instanceId.setTextContent("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        docId.appendChild(instanceId);

        Element type = doc.createElement("sbdh:Type");
        type.setTextContent("Events");
        docId.appendChild(type);

        Element creationDate = doc.createElement("sbdh:CreationDateAndTime");
        creationDate.setTextContent("2022-01-13T11:53:31.000Z");
        docId.appendChild(creationDate);
        sbdh.appendChild(docId);

        // Create EPCISBody
        Element epcisBody = doc.createElement("EPCISBody");
        epcisDoc.appendChild(epcisBody);

        Element eventList = doc.createElement("EventList");
        epcisBody.appendChild(eventList);

        Element objectEvent = doc.createElement("ObjectEvent");
        eventList.appendChild(objectEvent);

        Element eventTime = doc.createElement("eventTime");
        eventTime.setTextContent("2022-03-28T14:00:31.000Z");
        objectEvent.appendChild(eventTime);

        Element timeZoneOffset = doc.createElement("eventTimeZoneOffset");
        timeZoneOffset.setTextContent("+05:30");
        objectEvent.appendChild(timeZoneOffset);

        Element epcList = doc.createElement("epcList");
        Element epc = doc.createElement("epc");
        epc.setTextContent("urn:epc:id:sscc:xxxxxxxx.123456789");
        epcList.appendChild(epc);
        objectEvent.appendChild(epcList);

        Element action = doc.createElement("action");
        action.setTextContent("OBSERVE");
        objectEvent.appendChild(action);

        Element bizStep = doc.createElement("bizStep");
        bizStep.setTextContent("urn:epcglobal:cbv:bizstep:receiving");
        objectEvent.appendChild(bizStep);

        Element disposition = doc.createElement("disposition");
        disposition.setTextContent("urn:epcglobal:cbv:disp:in_progress");
        objectEvent.appendChild(disposition);

        Element readPoint = doc.createElement("readPoint");
        Element readId = doc.createElement("id");
        readId.setTextContent("urn:epc:id:sgln:xxxxxxxx.5555.0");
        readPoint.appendChild(readId);
        objectEvent.appendChild(readPoint);

        Element bizLocation = doc.createElement("bizLocation");
        Element locId = doc.createElement("id");
        locId.setTextContent("urn:epc:id:sgln:xxxxxxxx.5555.0");
        bizLocation.appendChild(locId);
        objectEvent.appendChild(bizLocation);

        // Convert to String
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    public static String createSOAPRequest(String sscc, String gln) throws Exception {
        // Create a SOAP message
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Create SOAP envelope
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
        envelope.addNamespaceDeclaration("urn", "urn:sap-com:document:sap:soap:functions:mc-style");
        envelope.addNamespaceDeclaration("sbdh", "http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader");
        envelope.addNamespaceDeclaration("epcis", "urn:epcglobal:epcis:xsd:1");
        envelope.addNamespaceDeclaration("cbvmda", "urn:epcglobal:cbv:mda");
        envelope.addNamespaceDeclaration("tatmeen", "http://tatmeen.ae/epcis/");

        // Remove default header
        SOAPHeader soapHeader = envelope.getHeader();
        soapHeader.detachNode();

        // Build the SOAP body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement epcisDocument = soapBody.addChildElement("EPCISDocument", "epcis");
        epcisDocument.addAttribute(envelope.createName("schemaVersion"), "1.2");
        epcisDocument.addAttribute(envelope.createName("creationDate"), "2022-01-10T18:51:26Z");

        // Add EPCISHeader
        SOAPElement epcisHeader = epcisDocument.addChildElement("EPCISHeader", "epcis");
        SOAPElement standardBusinessDocumentHeader = epcisHeader.addChildElement("StandardBusinessDocumentHeader", "sbdh");

        standardBusinessDocumentHeader.addChildElement("HeaderVersion", "sbdh").addTextNode("1.3");

        // Sender
        SOAPElement sender = standardBusinessDocumentHeader.addChildElement("Sender", "sbdh");
        sender.addChildElement("Identifier", "sbdh").addAttribute(envelope.createName("Authority"), "GS1").addTextNode("xxxxxxxxxxxxx");

        // Receiver
        SOAPElement receiver = standardBusinessDocumentHeader.addChildElement("Receiver", "sbdh");
        receiver.addChildElement("Identifier", "sbdh").addAttribute(envelope.createName("Authority"), "GS1").addTextNode("xxxxxxxxxxxxx");

        // DocumentIdentification
        SOAPElement documentIdentification = standardBusinessDocumentHeader.addChildElement("DocumentIdentification", "sbdh");
        documentIdentification.addChildElement("Standard", "sbdh").addTextNode("EPCGlobal");
        documentIdentification.addChildElement("TypeVersion", "sbdh").addTextNode("1.0");
        documentIdentification.addChildElement("InstanceIdentifier", "sbdh").addTextNode(UUID.randomUUID().toString().replace("-", ""));
        documentIdentification.addChildElement("Type", "sbdh").addTextNode("Events");
        documentIdentification.addChildElement("CreationDateAndTime", "sbdh").addTextNode("2022-01-13T11:53:31.000Z");

        // Add EPCISBody
        SOAPElement epcisBody = epcisDocument.addChildElement("EPCISBody", "epcis");
        SOAPElement eventList = epcisBody.addChildElement("EventList", "epcis");

        // Add ObjectEvent
        SOAPElement objectEvent = eventList.addChildElement("ObjectEvent", "epcis");
        objectEvent.addChildElement("eventTime", "epcis").addTextNode("2022-03-28T14:00:31.000Z");
        objectEvent.addChildElement("eventTimeZoneOffset", "epcis").addTextNode("+05:30");

        // Add EPC list
        SOAPElement epcList = objectEvent.addChildElement("epcList", "epcis");
        epcList.addChildElement("epc", "epcis").addTextNode("urn:epc:id:sscc:" + sscc + ".123456789");

        objectEvent.addChildElement("action", "epcis").addTextNode("OBSERVE");
        objectEvent.addChildElement("bizStep", "epcis").addTextNode("urn:epcglobal:cbv:bizstep:receiving");
        objectEvent.addChildElement("disposition", "epcis").addTextNode("urn:epcglobal:cbv:disp:in_progress");

        // Add readPoint and bizLocation
        SOAPElement readPoint = objectEvent.addChildElement("readPoint", "epcis");
        readPoint.addChildElement("id", "epcis").addTextNode("urn:epc:id:sgln:" + gln + ".5555.0");

        SOAPElement bizLocation = objectEvent.addChildElement("bizLocation", "epcis");
        bizLocation.addChildElement("id", "epcis").addTextNode("urn:epc:id:sgln:" + gln + ".5555.0");

        // Save the message changes
        soapMessage.saveChanges();

        return getSOAPMessageAsString(soapMessage);
    }

    private static String getSOAPMessageAsString(SOAPMessage soapMessage) throws Exception {
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        soapMessage.writeTo(outputStream);
        return new String(outputStream.toByteArray(), "UTF-8");
    }
}
