package com.example.iotmobius;

public class RemoteItem {
    private int item_type;
    private String item_name;

    public RemoteItem(int item_type) {
        this.item_type = item_type;
    }

    public RemoteItem(int item_type, String item_name){
        this.item_type = item_type;
        this.item_name = item_name;
    }

    public int getitem_type() {
        return item_type;
    }

    public void setitem_type(int item_type) {
        this.item_type = item_type;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
