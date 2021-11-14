package com.example.iotmobius;


public class Record {
    //관찰일지의 내용과, 관련한 동작을 저장한 클래스.
    private String id;
    private String image;
    private String title;
    private String intext;
    private String date;
    private com.example.iotmobius.State state;
    private String comments = "선생님 확인이 아직 완료되지 않았습니다.";





    public Record() {

    }
    public Record(String id, String image, String title, String intext, State state, String comments){
        //관찰일지 클래스 생성자
        this.id = id;
        this.image = image;
        this.title = title;
        this.intext = intext;
        this.date = date;
        this.state = state;
        this.comments = comments;
    }

    //==========================getter and setter======================
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public com.example.iotmobius.State getState() {
        return state;
    }

    public void setState(com.example.iotmobius.State state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntext() {
        return intext;
    }

    public void setIntext(String intext) {
        this.intext = intext;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
