package cn.com.efuture.o2o.backend.mybatis.entity;

import java.io.Serializable;

public class Channel implements Serializable{

    private Integer channelId;

    private String channelName;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
