package com.addRequest;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.utility.SolrUtility;

public class JMSListener {
	
	private static SolrUtility solrUtility;
	
	public static void main(String[] args) {
	
		solrUtility=new SolrUtility();
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
				if (noMessageToFetch > KafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT) {
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
				solrUtility.insertDocument(record.value());
				//System.out.println("Record partition " + record.partition());
				//System.out.println("Record offset " + record.offset());
			});
			consumer.commitAsync();
		}
		consumer.close();
	}

	public SolrUtility getSolrUtility() {
		return solrUtility;
	}

	public void setSolrUtility(SolrUtility solrUtility) {
		this.solrUtility = solrUtility;
	}

	
}
