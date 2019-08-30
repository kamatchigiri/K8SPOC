package com.kafka;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class App {
	public static void main(String[] args) {
	String sampleXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
			"   <SOAP-ENV:Header/>\n" + 
			"   <SOAP-ENV:Body>\n" + 
			"      <ns2:getEmployeeResponse xmlns:ns2=\"http://fedex.com/TestService\">\n" + 
			"         <ns2:employee>\n" + 
			"            <ns2:objId>300</ns2:objId>\n" + 
			"            <ns2.contactTypeCode>PRI</ns2.contactTypeCode>\n" + 
			"             <ns2.companyName>Grissom Afb FireDept</ns2.companyName>\n" + 
			"			 <ns2.address>\n" + 
			"                  <ns2.streetLine>100 UNIVERSAL CITY PLZ</ns2.streetLine>\n" + 
			"				  <ns2.city>UNIVERSAL CITY</ns2.city>\n" + 
			"				  <ns2.stateCode>CA</ns2.stateCode>\n" + 
			"                  <ns2.postalCode>N8N3N7</ns2.postalCode>\n" + 
			"                  <ns2.countryCode>US</ns2.countryCode>\n" + 
			"               </ns2.address>\n" + 
			"               <ns2.teleCom>\n" + 
			"                  <ns2.areaCode>519</ns2.areaCode>\n" + 
			"                  <ns2.phoneNumber>2076228378</ns2.phoneNumber>\n" + 
			"               </ns2.teleCom>\n" + 
			"         </ns2:employee>\n" + 
			"      </ns2:getEmployeeResponse>\n" + 
			"   </SOAP-ENV:Body>\n" + 
			"</SOAP-ENV:Envelope>";
	//System.out.println("Sample xml:::: " + sampleXML);
	//runProducer(sampleXML);
		runConsumer();
	}

	static void runConsumer() {
		Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
		System.out.println("Check1");

		int noMessageToFetch = 0;

		while (true) {
			final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
			if (consumerRecords.count() == 0) {
				System.out.println("Check2");
				noMessageToFetch++;
				if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT) {
					System.out.println("Check3");
					break;
				}
				else {
					System.out.println("Check4");
					continue;

				}
			}
			System.out.println("Check5");
			System.out.println("cons " + consumerRecords.count());
			consumerRecords.forEach(record -> {
				//System.out.println("Record Key " + record.key());
				System.out.println("Record value " + record.value());
				//System.out.println("Record partition " + record.partition());
				//System.out.println("Record offset " + record.offset());
			});
			consumer.commitAsync();
		}
		consumer.close();
	}

	/*
	 * static void runProducer() { Producer<Long, String> producer =
	 * ProducerCreator.createProducer();
	 * 
	 * for (int index = 0; index < IKafkaConstants.MESSAGE_COUNT; index++) { final
	 * ProducerRecord<Long, String> record = new ProducerRecord<Long,
	 * String>(IKafkaConstants.TOPIC_NAME, "This is record " + index); try {
	 * RecordMetadata metadata = producer.send(record).get();
	 * System.out.println("Record sent with key " + index + " to partition " +
	 * metadata.partition() + " with offset " + metadata.offset()); } catch
	 * (ExecutionException e) { System.out.println("Error in sending record");
	 * System.out.println(e); } catch (InterruptedException e) {
	 * System.out.println("Error in sending record"); System.out.println(e); } } }
	 */
	
	
	static void runProducer(String sampleXML) {
		Producer<Long, String> producer = ProducerCreator.createProducer();

		//for (int index = 0; index < IKafkaConstants.MESSAGE_COUNT; index++) {
			final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME,
					sampleXML);
			try {
				RecordMetadata metadata = producer.send(record).get();
				System.out.println("Record sent " + sampleXML + " to partition " + metadata.partition()  + " with offset " + metadata.offset());
			} catch (ExecutionException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			} catch (InterruptedException e) {
				System.out.println("Error in sending record");
				System.out.println(e);
			}
	//	}
	}
}
