package cn.com.efuture.o2o.backend.queue.service;

public interface MessageHandler {

    void onMessage(String message);
}
