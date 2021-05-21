package com.leggett.media.binaryfile;

import javax.persistence.Entity;

@Entity
public class Audio extends BinaryFile{

    protected Audio(){}

    public Audio(String fileName){
        this.setFileName(fileName);
    }
}
