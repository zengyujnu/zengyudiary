package student.jnu.com.zengyudiary;

import java.io.Serializable;

public class Diary implements Serializable{
    private  int type;
    //private int position;
    private String YEAR;
    private String MONTH;
    private String day_in_week;
    private int day_in_month;
    private String diary_content;

    public Diary(){}

    /*public Diary(int type,String YEAR,String MONTH,String day_in_week,int day_in_month,String diary_content){
        this.type=type;
        this.YEAR=YEAR;
        this.MONTH=MONTH;
        this.day_in_week=day_in_week;
        this.day_in_month=day_in_month;
        this.diary_content=diary_content;
    }*/

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /*public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }*/

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getDay_in_week() {
        return day_in_week;
    }

    public void setDay_in_week(String day_in_week) {
        this.day_in_week = day_in_week;
    }

    public int getDay_in_month(){
        return day_in_month;
    }

    public void setDay_in_month(int day_in_month) {
        this.day_in_month = day_in_month;
    }

    public String getDiary_content() {
        return diary_content;
    }

    public void setDiary_content(String diary_content) {
        this.diary_content = diary_content;
    }
}