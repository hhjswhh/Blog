package tech.acodesigner.po;

/**
 * Created by 77239 on 2017/2/10/0010.
 */
public class MessagePo {

    private int messageId;
    private int userId;
    private int messageType;
    private int pid;
    private String content;
    private long pubDate;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPubDate() {
        return pubDate;
    }

    public void setPubDate(long pubDate) {
        this.pubDate = pubDate;
    }
}
