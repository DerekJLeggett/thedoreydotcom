package com.leggett.media.binaryfile;

import javax.persistence.Entity;

@Entity
public class Video extends BinaryFile{

    protected Video(){}

    public Video(String fileName){
        this.setFileName(fileName);
    }
}
