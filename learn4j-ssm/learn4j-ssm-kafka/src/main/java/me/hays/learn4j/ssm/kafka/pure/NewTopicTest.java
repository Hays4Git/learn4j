package me.hays.learn4j.ssm.kafka.pure;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

public class NewTopicTest {
	

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "120.78.191.84:9092");
		AdminClient adminClient = AdminClient.create(properties);
		ArrayList<NewTopic> topics = new ArrayList<NewTopic>();
		NewTopic newTopic = new NewTopic("topic-test", 1, (short)1);
		topics.add(newTopic);
		CreateTopicsResult result = adminClient.createTopics(topics);
		try {
			result.all().get();
			System.out.println("done======done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
