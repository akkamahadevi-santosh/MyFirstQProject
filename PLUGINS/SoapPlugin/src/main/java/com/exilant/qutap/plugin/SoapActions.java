package com.exilant.qutap.plugin;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;

public class SoapActions {

	private SOAPConnectionFactory soapConnectionFactory;
	private SOAPConnection soapConnection;
	private MessageFactory messageFactory;
	private SOAPMessage soapMessage;
	private SOAPPart soapPart;
	private SOAPEnvelope soapEnvelope;
	JSONObject respJSON = new JSONObject();
	StringWriter errors = new StringWriter();
	String result1 = null;
	String res = "";
	ArrayList<String> value;
	List<String> list;

	public JSONObject isWSDLAvailable(String wsdlAddr) {
		HttpURLConnection c = null;

		value = new ArrayList<String>();
		value.add(wsdlAddr);
		try {

			URL u = new URL(wsdlAddr);
			c = (HttpURLConnection) u.openConnection();
			c.getURL();

			
			c.getInputStream();
			
			if (c.getResponseCode() == 200 && c.getURL().toString().endsWith("wsdl")) {

				respJSON.put("status", "PASS");
				respJSON.put("response", c.getResponseCode());
				respJSON.put("error", "");
			}

		} catch (Exception e) {

			e.printStackTrace(new PrintWriter(errors));

			respJSON.put("status", "FAIL");
			respJSON.put("response", "");
			respJSON.put("error", errors.toString());
		} finally {
			if (c != null)
				c.disconnect();
		}
		return respJSON;

	}

	public JSONObject testForresult(String value1, String value2, String value3)
			throws IOException, TransformerFactoryConfigurationError {

		URL u = new URL(value1);
		
		HttpURLConnection c = null;
		c = (HttpURLConnection) u.openConnection();
		if (c.getResponseCode() == 200 && c.getURL().toString().endsWith("wsdl")) {

			try {

				soapConnectionFactory = SOAPConnectionFactory.newInstance();
				soapConnection = soapConnectionFactory.createConnection();

				messageFactory = MessageFactory.newInstance();
				soapMessage = messageFactory.createMessage();

				soapPart = soapMessage.getSOAPPart();
				soapEnvelope = soapPart.getEnvelope();

				soapEnvelope.addNamespaceDeclaration("web", "http://www.webserviceX.NET/");

				// change header's attribute
				MimeHeaders mimeHeader = soapMessage.getMimeHeaders();
				mimeHeader.setHeader("SOAPAction", "http://www.webserviceX.NET/ConversionRate");

				SOAPBody soapBody = soapEnvelope.getBody();
				QName bodyName = new QName("http://www.webserviceX.NET/", "ConversionRate", "web");
				SOAPBodyElement bodyElement = soapBody.addBodyElement(bodyName);
				QName n = new QName("http://www.webserviceX.NET/", "FromCurrency", "web");
				QName n1 = new QName("http://www.webserviceX.NET/", "ToCurrency", "web");

				bodyElement.addChildElement(n).addTextNode(value2);
				bodyElement.addChildElement(n1).addTextNode(value3);
				soapMessage.saveChanges();

				
				soapMessage.writeTo(System.out);

				SOAPMessage soapResponseMessage = soapConnection.call(soapMessage, value1);
				

				// Create transformer
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();

				// Get reply content
				Source source = soapResponseMessage.getSOAPPart().getContent();

				// Set output transformation
				StringWriter writer = new StringWriter();
				StreamResult sResult = new StreamResult(writer);
				transformer.transform(source, sResult);
				result1 = writer.toString();
				

				respJSON.put("status", "PASS");
				respJSON.put("response", writer.toString());
				respJSON.put("error", "");
				soapConnection.close();

			} catch (Exception e) {

				e.printStackTrace(new PrintWriter(errors));

				respJSON.put("status", "FAIL");
				respJSON.put("response", "");
				respJSON.put("error", errors.toString());
			} finally {
				if (c != null)
					c.disconnect();
			}
		}

		return respJSON;
	}

}
