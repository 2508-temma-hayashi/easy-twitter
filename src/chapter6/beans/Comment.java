package chapter6.beans;

import java.io.Serializable;
import java.util.Date;



public class Comment implements Serializable{

    private int id;
    private String text;
    private int user_id;
    private int message_id;
    private Date createdDate;
    private Date updatedDate;


	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getMessageId() {
        return message_id;
    }
    public void setMessageId(int message_id) {
        this.message_id = message_id;
    }

    public int getUserId() {
        return user_id;
    }
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}


