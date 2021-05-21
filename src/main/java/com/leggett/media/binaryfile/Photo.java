package com.leggett.media.binaryfile;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Photo extends BinaryFile{
    private Date dateTaken;

    protected Photo(){}

    public Photo(String fileName, Date dateTaken){
        this.setFileName(fileName);
       this.dateTaken = dateTaken;
    }

    public Date getDateTaken(){
        return this.dateTaken;
    }

    public void setDateTaken(Date dateTaken){
        this.dateTaken = dateTaken;
    }
}
