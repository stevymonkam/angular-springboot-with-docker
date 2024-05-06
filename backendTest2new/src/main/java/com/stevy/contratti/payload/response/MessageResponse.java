package com.stevy.contratti.payload.response;

import com.stevy.contratti.models.*;

import java.util.List;

public class MessageResponse {

    private  String action;
    private  String status;
    private  boolean success;
    private  String message;
    private FileContrat file;
    private Contrat data;
    private List<Contrat> list;
    private Messages messages;
    private User user;

    public MessageResponse(String action, boolean b, List<Contrat> l) {
        this.action = action;
        this.success = b;
        this.list = l;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(String action,String status,boolean success,String message,Contrat data) {
        this.action = action;
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public MessageResponse(String action, boolean success, String message, User user) {
        this.action = action;
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public MessageResponse(String action,String status,boolean success,String message,Messages messages) {
        this.action = action;
        this.status = status;
        this.success = success;
        this.message = message;
        this.messages = messages;

    }

    public MessageResponse(String action,String status,boolean success,String message,List<Contrat> list) {
        this.action = action;
        this.status = status;
        this.success = success;
        this.message = message;
        this.list = list;
    }

    public MessageResponse(String action,String status,boolean success,String message,FileContrat file) {
        this.action = action;
        this.status = status;
        this.success = success;
        this.message = message;
        this.file = file;
    }
    public MessageResponse(String action,String status,boolean success,String message,FileContrat file,Contrat c) {
        this.action = action;
        this.status = status;
        this.success = success;
        this.message = message;
        this.file = file;
        this.data = c;
    }
    public MessageResponse(String action,String status,boolean success,String message) {
        this.action = action;
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public FileContrat getFile() {
        return file;
    }

    public void setFile(FileContrat file) {
        this.file = file;
    }

    public Contrat getData() {
        return data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setData(Contrat data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Contrat> getList() {
        return list;
    }

    public void setList(List<Contrat> list) {
        this.list = list;
    }

}
