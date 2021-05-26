package com.leggett.media.binaryfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class BinaryFile {
    @Column(insertable = false, updatable = false) private String dtype;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;
	public String fileName;
}
