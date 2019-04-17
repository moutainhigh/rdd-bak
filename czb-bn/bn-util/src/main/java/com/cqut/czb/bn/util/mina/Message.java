package com.cqut.czb.bn.util.mina;



import java.io.Serializable;

public class Message implements Serializable {
    private String user;
    private String toUser;
    private String message;
    private int type;//消息类型，1为登录，0为普通消息

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String toString(){
        return "from " + user + " to " + toUser + " " + message;
    }
}
