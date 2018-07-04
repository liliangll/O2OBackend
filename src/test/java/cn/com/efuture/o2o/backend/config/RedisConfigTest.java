package cn.com.efuture.o2o.backend.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisConfigTest {

    protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;

    @Value("$(RMQ.SUBSCRIBE)")
    private String queueName;

    /*
    * Run Producter
    * */
    @Test
    public void producterTest() throws Exception {
        for(int i=1; i<10; i++ ){
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            redisTemplate.opsForList().leftPush("MQ", "{\"chanel\": \"MT\", \"type\": \"orderRecive\", \"data\": {\"a\": 123, \"b\": \"hello\"}, \"timastamp\": " + ts.toString() + "}"+ i);
        }
    }

    /*
    * Run 2nd step
    * */
//    @Test
    public void consumerTest() throws Exception {
        while(true){
            Object msg = redisTemplate.opsForList().rightPop("MQ", 36000L, TimeUnit.MINUTES);
            if (msg == null) {
                continue;
            }
            String strMsg = msg.toString();
            logger.debug(strMsg);
        }
    }

}