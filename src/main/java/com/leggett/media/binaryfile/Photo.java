package com.leggett.media.binaryfile;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Photo extends BinaryFile{
    public Photo(String fileName, Date dateTaken, Double latitude, Double longitude) {
        this.fileName = fileName;
        this.dateTaken = dateTaken;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Photo(){}

    private Date dateTaken;
    private Double latitude;
    private Double longitude;
}
