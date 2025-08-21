package chapter6.beans;
//JAVAとデータベースをつなぐ,格納庫みたいなもん

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private int id;
    private String account;
    private String name;
    private String email;
    private String password;
    private String description;
    private Date createdDate;
    private Date updatedDate;

    // getter/setterは省略されているので、自分で記述しましょう。
    //id
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
    
    
}