package com.leggett.media.binaryfile;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Video extends BinaryFile{

    public Video(String fileName) {
        this.fileName = fileName;
    }

}
