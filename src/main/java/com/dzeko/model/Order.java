package com.dzeko.model;

import java.time.LocalDate;

public class Order {

    private Integer idorder;
    private String customer;
    private String telephone;
    private String address;
    private int userid;    
    private LocalDate date_create;
    private String text;
    private boolean finished = false;
    private boolean approve = false;
    private boolean deleted = false;
    private boolean dimensions = false;
    private String creator_user;
    private Integer creator_user_id;

    public Order(String customer, String text) {
        this.customer = customer;
        this.text = text;
    }

    public Order(Integer idorder, String text) {
        this.idorder = idorder;
        this.text = text;
    }
        
    public Order() {
    }
        
    public Integer getIdorder() {
        return idorder;
    }

    public void setIdorder(Integer idorder) {
        this.idorder = idorder;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
        
    public LocalDate getDate_create() {
        return date_create;
    }

    public void setDate_create(LocalDate date_create) {
        this.date_create = date_create;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
    
    public Order(Integer idorder, String customer, int userid, LocalDate date_create) {
        this.idorder = idorder;
        this.customer = customer;
        this.userid = userid;
        this.date_create = date_create;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDimensions() {
        return dimensions;
    }

    public void setDimensions(boolean dimensions) {
        this.dimensions = dimensions;
    }

    public String getCreator_user() {
        return creator_user;
    }

    public void setCreator_user(String creator_user) {
        this.creator_user = creator_user;
    }

    public Integer getCreator_user_id() {
        return creator_user_id;
    }

    public void setCreator_user_id(Integer creator_user_id) {
        this.creator_user_id = creator_user_id;
    }
    
    
    
        
    @Override
    public String toString() {
        return "Order{" + "idorder=" + idorder + ", customer=" + customer + ", userid=" + userid + ", date_create=" + date_create + ", text=" + text + ", finished=" + finished + '}';
    }

    
    
}
