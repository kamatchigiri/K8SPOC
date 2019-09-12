package com.fedex.Endpoint;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import com.fedex.jms.ProducerCreator;
import com.fedex.testservice.AddAccountRequest;
import com.fedex.utility.JMSUtility;
import com.fedex.utility.KafkaConstants;


@Component
public class JMSPublisher {
	
	private  Producer<Long, String> producer=null;
	
	private static String filePath="X:\\KafkaProducer\\webservice.xml";

	public void publishMessage(AddAccountRequest request)
	{
		
		
		ProducerRecord<Long, String> token=null;
		String xmlString=null;
		try {	
			JMSUtility.convertObjectToXML(request, filePath);
			if(producer==null)
			{
				producer=ProducerCreator.createProducer();
			}	
			xmlString=JMSUtility.readXMLFile(filePath);
			
			token=new ProducerRecord<Long, String>(KafkaConstants.TOPIC_NAME,xmlString);
			producer.send(token);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
