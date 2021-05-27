package com.leggett.media.binaryfile;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Video extends BinaryFile{

    public Video(String fileName, Date dateTaken, double latitude, double longitude) {
        this.fileName = fileName;
        this.dateTaken = dateTaken;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
