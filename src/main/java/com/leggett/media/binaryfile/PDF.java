package com.leggett.media.binaryfile;

import javax.persistence.Entity;

@Entity
public class PDF extends BinaryFile {
    protected PDF() {
    }

    public PDF(String fileName) {
        this.setFileName(fileName);
    }
}
