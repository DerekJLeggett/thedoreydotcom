package com.leggett.media.video;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Video {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	private String fileName;

    protected Video(){}

    public Video(String fileName){
        this.fileName = fileName;
    }

    public Long getId(){
        return this.id;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }
}
