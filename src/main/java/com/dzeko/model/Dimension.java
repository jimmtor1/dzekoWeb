package com.dzeko.model;


public class Dimension {
    
    private Integer iddimension;
    private int orderid;
    private float width;
    private float height;
    private String opening;
    private float depth; 
    private int quantity;
    private String wingShorter;
    private String wingLonger;
    private String note;
    private String bigNote;    

    public Integer getIddimension() {
        return iddimension;
    }

    public void setIddimension(Integer iddimension) {
        this.iddimension = iddimension;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }
    
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public String getOpening() {
        return opening;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getWingShorter() {
        return wingShorter;
    }

    public void setWingShorter(String wingShorter) {
        this.wingShorter = wingShorter;
    }

    public String getWingLonger() {
        return wingLonger;
    }

    public void setWingLonger(String wingLonger) {
        this.wingLonger = wingLonger;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBigNote() {
        return bigNote;
    }

    public void setBigNote(String bigNote) {
        this.bigNote = bigNote;
    }

    @Override
    public String toString() {
        return "Dimension{" + "iddimension=" + iddimension + ", orderid=" + orderid + ", width=" + width + ", height=" + height + ", opening=" + opening + ", depth=" + depth + ", quantity=" + quantity + ", wingShorter=" + wingShorter + ", wingLonger=" + wingLonger + ", note=" + note + ", bigNote=" + bigNote + '}';
    }
    
    
    
}
