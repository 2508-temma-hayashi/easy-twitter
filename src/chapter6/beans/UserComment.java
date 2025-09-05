package chapter6.beans;

import java.util.Date;

public class UserComment {
    private int id;
    private String text;
    private int user_id;
    private int message_id;
    private Date createdDate;
    private Date updatedDate;
    private int users_id;
    private String account;
    private String name;
    private String email;
    private String password;
    private String description;
    private Date user_createdDate;
    private Date user_updatedDate;


    public int getId() {
    	return this.id;
    }
    public void setId(int id) {
    	this.id = id;
    }

    //account
    public String getAccount() {
    	return this.account;
    }
    public void setAccount(String account) {
    	this.account = account;
    }

    //name
    public String getName() {
    	return this.name;
    }
    public void setName(String name) {
    	this.name = name;
    }

    // email
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // password
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // description
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // createdDate
    public Date getCreatedDate() {
        return this.createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    // updatedDate
    public Date getUpdatedDate() {
        return this.updatedDate;
    }
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getUsersId() {
        return users_id;
    }
    public void setUsersId(int users_id) {
        this.id = users_id;
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

    public Date getUserCreatedDate() {
        return user_createdDate;
    }
    public void setUserCreatedDate(Date user_createdDate) {
        this.user_createdDate = user_createdDate;
    }

    public Date getUserUpdatedDate() {
        return user_updatedDate;
    }
    public void setUpUserdatedDate(Date user_updatedDate) {
        this.user_updatedDate = user_updatedDate;
    }
}
