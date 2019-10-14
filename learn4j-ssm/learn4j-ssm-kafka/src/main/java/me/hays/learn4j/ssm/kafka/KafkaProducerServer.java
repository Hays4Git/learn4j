package me.hays.learn4j.ssm.kafka;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.alibaba.fastjson.JSON;

@Component
public class KafkaProducerServer{

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public KafkaMesConstant sndMesForTemplate(String topic, Object value, boolean ifPartition, 
            Integer partitionNum, String role){
        String key = role+"-"+value.hashCode();
        String valueString = JSON.toJSONString(value);
        if(ifPartition){
            //表示使用分区
            int partitionIndex = getPartitionIndex(key, partitionNum);
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, partitionIndex, key, valueString);
            return checkProRecord(result);
        }else{
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, key, valueString);
            return checkProRecord(result);
        }
    }

    private int getPartitionIndex(String key, int partitionNum){
        if (key == null) {
            Random random = new Random();
            return random.nextInt(partitionNum);
        }
        else {
            int result = Math.abs(key.hashCode())%partitionNum;
            return result;
        }
    }
    
    @SuppressWarnings("rawtypes")
    private KafkaMesConstant checkProRecord(ListenableFuture<SendResult<String, String>> res){
        if(res!=null){
            try {
                SendResult r = res.get();//检查result结果集
                /*检查recordMetadata的offset数据，不检查producerRecord*/
                Long offsetIndex = r.getRecordMetadata().offset();
                if(offsetIndex!=null && offsetIndex>=0){
                    return KafkaMesConstant.SUCCESS;
                }else{
                	return KafkaMesConstant.NO_OFFSET;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return KafkaMesConstant.SEND_ERROR;
            } 
        }else{
            return KafkaMesConstant.NO_RESULT;
        }
    }
    

}