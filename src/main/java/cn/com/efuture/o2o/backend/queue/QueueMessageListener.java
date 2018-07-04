package cn.com.efuture.o2o.backend.queue;

import cn.com.efuture.o2o.backend.queue.service.MessageHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


//@WebListener
public class QueueMessageListener implements ServletContextListener {

    protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${rmq.subscribe}")
    private String queueName;

    @Autowired
    private MessageHandler messageHandler;

    private Executor executor;


//    @PostConstruct
//    public void init() throws Exception {
//        logger.debug("QueueMessage-init");
//    }
//
//    public void destoy() {
//
//    }

    public QueueMessageListener() {
    }

    public QueueMessageListener(final String queueName, MessageHandler messageHandler) {
        this.queueName = queueName;
        this.messageHandler = messageHandler;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.debug("Start the AsyncMessageListener");
        new Thread(new AsyncMessageListener()).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private class AsyncMessageListener implements Runnable {
        @Override
        public void run() {
            logger.debug("Begin starting the AsyncMessageListener run()");
            while(true) {
                try {
                    process();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 同步执行。队列监听器与消息处理器是同一个线程。
     *
     * @param
     * @return
     * @throws Exception
     */
    public void process() throws Exception{
        logger.debug("Try to get the message from the queue:" + queueName);
        if (executor == null) {
            while(true) {
                String message = (String) redisTemplate.opsForList().rightPop(queueName, 36000L, TimeUnit.MINUTES);
                if (StringUtils.isBlank(message)) {
                    continue;
                }
//                logger.debug(message);
                messageHandler.onMessage(message);
            }
        }else {
            process(executor);
        }
    }


    public void process(Executor executor) throws Exception {
        logger.debug("Try to get the message from the 2:" + queueName);
        while(true) {
            String message = (String) redisTemplate.opsForList().rightPop(queueName, 36000L, TimeUnit.MINUTES);
//            logger.debug(message);
            submitTask(executor, message);
        }
    }

    private void submitTask(Executor executor, final String message) {
        if (StringUtils.isNotBlank(message)) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    messageHandler.onMessage(message);
                }
            });
        }
    }
}
