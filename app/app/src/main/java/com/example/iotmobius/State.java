package com.example.iotmobius;

public class State {
   //식물의 상태값을 저장하는 클래스
   double light;
   double soil;
   double temp;
   double humid;

   public State() {
   }

   public State(double light, double soil, double temp, double humid){
   //식물 상태 생성자
   this.light = light;
   this.temp = temp;
   this.soil = soil;
   this.humid = humid;
   }

//==============getter and setter===========================
   public double getLight() {
      return light;
   }

   public void setLight(double light) {
      this.light = light;
   }

   public double getTemp() {
      return temp;
   }

   public void setTemp(double temp) {
      this.temp = temp;
   }

   public double getSoil() {
      return soil;
   }

   public void setSoil(double soil) {
      this.soil = soil;
   }

   public double getHumid() {
      return humid;
   }

   public void setHumid(double humid) {
      this.humid = humid;
   }



}
