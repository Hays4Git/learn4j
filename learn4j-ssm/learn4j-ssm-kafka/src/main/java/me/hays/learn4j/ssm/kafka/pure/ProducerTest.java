package me.hays.learn4j.ssm.kafka.pure;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerTest {

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "120.78.191.84:9092");
		properties.put("acks", "all");
		properties.put("retries", 0);
		properties.put("batch.size", 16384);
		properties.put("linger.ms", 1);
		properties.put("buffer.memory", 33554432);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String, String> producer = new KafkaProducer<String, String>(properties);
		for(int i = 0; i<100; i++) {
			producer.send(new ProducerRecord<String, String>("topic-test", Integer.toString(i), Integer.toString(i)));
		}
		producer.close();
	}

}
