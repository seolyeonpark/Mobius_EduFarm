package com.example.iotmobius;

public class DataMarket {
    String id; //장터에 올라온 아이템 정보
    String title; //장터에 올라온 아이템 상세이름
    String qty; //장터에 올라온 아이템 수

    //public DataMarket(int id, String title, String author){
   //     this.id = id;
   //     this.title = title;
   //     this.author = author;
   // }

  //  public DataMarket() {

    //}

    public String getId() {   return id;  }

    public void setId(String id) {this.id = id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title= title;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
