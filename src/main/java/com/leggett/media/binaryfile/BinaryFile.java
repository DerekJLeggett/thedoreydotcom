package com.leggett.media.binaryfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BinaryFile {
    @Column(insertable = false, updatable = false) private String dtype;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	private String fileName;

    protected BinaryFile(){}

    public BinaryFile(String fileName){
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
