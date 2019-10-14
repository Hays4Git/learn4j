package me.hays.learn4j.ssm.kafka;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaProducerTest extends SpringServiceTestBase{

	@Autowired
	private KafkaProducerServer kafkaProducer;
	
	@Test
	public void produceTest(){
        
        String topic = "other_test_topic";
        String value = "test";
        boolean ifPartition = false;
        Integer partitionNum = 1;
        String role = "test221234221";//用来生成key
        KafkaMesConstant res = kafkaProducer.sndMesForTemplate
                (topic, value, ifPartition, partitionNum, role);
        
        System.out.println("测试结果如下：===============");
        String message = (String)res.getCode();
        String code = (String)res.getMessage();
        
        System.out.println("code:"+code);
        System.out.println("message:"+message);
    }
	
}
