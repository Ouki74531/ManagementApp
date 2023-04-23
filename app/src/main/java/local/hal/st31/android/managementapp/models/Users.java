package local.hal.st31.android.managementapp.models;

public class Users {
    String name;
    String email;
    String password;
    String picture;
    String userId;
    String lastMessage;

    public Users(){

    }

    public Users(String name, String email, String password, String picture, String userId, String lastMessage){
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
