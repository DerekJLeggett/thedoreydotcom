package com.leggett.media.audio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	private String fileName;

    protected Audio(){}

    public Audio(String fileName){
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
