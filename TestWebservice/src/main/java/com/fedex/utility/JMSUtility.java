package com.fedex.utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.fedex.testservice.AddAccountRequest;


public class JMSUtility {

	public static void convertObjectToXML(AddAccountRequest request,
			String filePath) {
		JAXBContext context = null;
		Marshaller marshaller = null;
		try {
			context = JAXBContext.newInstance(AddAccountRequest.class);
			marshaller = context.createMarshaller();
			System.out.println("Token:");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			FileOutputStream file = new FileOutputStream(new File(
					filePath));

			marshaller.marshal(request, file);
			file.flush();
			file.close();
			marshaller = null;

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static AddAccountRequest convertXMLToObject(AddAccountRequest request, String xmlString)
	{
		try {
			JAXBContext context=JAXBContext.newInstance(AddAccountRequest.class);
			Unmarshaller unMarshaller=context.createUnmarshaller();			
			request=(AddAccountRequest)unMarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()));
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
		
		return request;
	}
	public static String readXMLFile(String filePath)
	{
		BufferedReader buffer=null;
		StringBuffer bufferStr=new StringBuffer();
		try {
			
			 buffer=new BufferedReader(new FileReader(new File(filePath)));
			String s=null;
			while ((s=buffer.readLine())!=null) {
				bufferStr.append(s);		
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally
		{
			
		}
		return bufferStr.toString();
	}
	
	
}
