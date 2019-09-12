package com.fedex.utility;

public interface KafkaConstants {
	//public static String KAFKA_BROKERS = "localhost:9092";
	public static String KAFKA_BROKERS = "c0018681.test.cloud.fedex.com:9092";
	
	//public static Integer MESSAGE_COUNT=10;
	
	public static String CLIENT_ID="client2";
	
	//public static String TOPIC_NAME="k8spoc1";
	public static String TOPIC_NAME="FirstKafkaTopic";
	
	public static String GROUP_ID_CONFIG="consumerGroup10";
	
	public static Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
	
	public static String OFFSET_RESET_LATEST="latest";
	
	public static String OFFSET_RESET_EARLIER="earliest";
	
	public static Integer MAX_POLL_RECORDS=1;
}
