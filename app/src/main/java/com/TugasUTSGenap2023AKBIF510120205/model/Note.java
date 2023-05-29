package com.TugasUTSGenap2023AKBIF510120205.model;

import java.io.Serializable;

public class Note implements Serializable {
    String id;
    String title;
    String desc;
    String date;

    public Note(String id, String title, String desc, String date){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


// 10120205 - Raya Adhary - IF5
