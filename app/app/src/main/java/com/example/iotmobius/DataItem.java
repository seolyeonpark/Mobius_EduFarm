package com.example.iotmobius;

public class DataItem {
    String user; //학생이름
    String item; //구매한 아이템 id
    String i_name; //구매한 아이템의 상세이름
    String point; //학생이 아이템을 구매한 가격



    public String getUser() {   return user;  }

    public void setUser(String user) {this.user = user;}

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item= item;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
