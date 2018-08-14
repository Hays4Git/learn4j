package me.hays.learn4j.ssm.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component("producerListener")
public class KafkaProducerListener implements ProducerListener{
    @Override
    public void onSuccess(String topic, Integer partition, Object key,
            Object value, RecordMetadata recordMetadata) {
        System.out.println("==========kafka发送数据成功==========");
        System.out.println("----------topic:"+topic);
        System.out.println("----------partition:"+partition);
        System.out.println("----------key:"+key);
        System.out.println("----------value:"+value);
        System.out.println("----------RecordMetadata:"+recordMetadata);
        System.out.println("==========kafka发送数据成功==========");
    }

    @Override
    public void onError(String topic, Integer partition, Object key,
            Object value, Exception exception) {
        System.out.println("==========kafka发送数据错误==========");
        System.out.println("----------topic:"+topic);
        System.out.println("----------partition:"+partition);
        System.out.println("----------key:"+key);
        System.out.println("----------value:"+value);
        System.out.println("----------Exception:"+exception);
        System.out.println("==========kafka发送数据错误==========");
        exception.printStackTrace();
    }

    @Override
    public boolean isInterestedInSuccess() {
        System.out.println("========kafkaProducer监听器启动========");
        return true;
    }

}