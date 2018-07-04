package cn.com.efuture.o2o.backend.queue.service;


import org.springframework.stereotype.Service;

@Service
public class MessageHandlerImpl implements MessageHandler {
    protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(String message) {
        try {
            logger.debug(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
